package com.example.flytrack.services;

import com.example.flytrack.dtos.NotificacionResponse;
import com.example.flytrack.entities.Notificacion;
import com.example.flytrack.entities.Reserva;
import com.example.flytrack.entities.Usuario;
import com.example.flytrack.entities.Vuelo;
import com.example.flytrack.enums.TipoNotificacion;
import com.example.flytrack.mappers.NotificacionMapper;
import com.example.flytrack.repositories.NotificacionRepository;
import com.example.flytrack.repositories.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final ReservaRepository reservaRepository;
    private final NotificacionMapper notificacionMapper;
    private final WebSocketNotificacionService webSocketService;

    public NotificacionService(NotificacionRepository notificacionRepository,
                                ReservaRepository reservaRepository,
                                NotificacionMapper notificacionMapper,
                                WebSocketNotificacionService webSocketService) {
        this.notificacionRepository = notificacionRepository;
        this.reservaRepository = reservaRepository;
        this.notificacionMapper = notificacionMapper;
        this.webSocketService = webSocketService;
    }

    public List<NotificacionResponse> listarPorUsuario(Usuario usuario) {
        return notificacionRepository.findByUsuarioOrderByFechaEnvioDesc(usuario).stream()
                .map(notificacionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<NotificacionResponse> listarNoLeidas(Usuario usuario) {
        return notificacionRepository.findByUsuarioAndLeida(usuario, false).stream()
                .map(notificacionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public long contarNoLeidas(Usuario usuario) {
        return notificacionRepository.countByUsuarioAndLeida(usuario, false);
    }

    @Transactional
    public void marcarComoLeida(Integer idNotificacion, Usuario usuario) {
        Notificacion notificacion = notificacionRepository.findById(idNotificacion)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        if (!notificacion.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            throw new RuntimeException("No tienes permiso para marcar esta notificación");
        }
        notificacion.setLeida(true);
        notificacionRepository.save(notificacion);
    }

    /**
     * Notifica a todos los pasajeros con reserva en el vuelo sobre el cambio de estado.
     * Se llama automáticamente desde VueloService.actualizar() cuando cambia el estado.
     */
    @Transactional
    public void notificarCambioEstadoVuelo(Vuelo vuelo) {
        List<Reserva> reservas = reservaRepository.findByVuelo(vuelo);

        String titulo = "Actualización de vuelo " + vuelo.getNumeroVuelo();
        String mensaje = "El estado del vuelo " + vuelo.getNumeroVuelo()
                + " ha cambiado a: " + vuelo.getEstado().name().replace("_", " ").toUpperCase();

        TipoNotificacion tipo = switch (vuelo.getEstado()) {
            case cancelado -> TipoNotificacion.cancelacion;
            case embarcando -> TipoNotificacion.embarque;
            default -> TipoNotificacion.cambio_vuelo;
        };

        for (Reserva reserva : reservas) {
            Usuario pasajero = reserva.getUsuario();
            Notificacion notificacion = new Notificacion();
            notificacion.setUsuario(pasajero);
            notificacion.setVuelo(vuelo);
            notificacion.setTitulo(titulo);
            notificacion.setMensaje(mensaje);
            notificacion.setTipo(tipo);

            NotificacionResponse dto = notificacionMapper.toResponse(notificacionRepository.save(notificacion));

            // Push en tiempo real vía WebSocket al pasajero específico
            webSocketService.enviarNotificacion(pasajero.getCorreo(), dto);
        }
    }

    @Transactional
    public NotificacionResponse crearNotificacionManual(Usuario usuario, Vuelo vuelo,
                                                         String titulo, String mensaje,
                                                         TipoNotificacion tipo) {
        Notificacion n = new Notificacion();
        n.setUsuario(usuario);
        n.setVuelo(vuelo);
        n.setTitulo(titulo);
        n.setMensaje(mensaje);
        n.setTipo(tipo);
        NotificacionResponse dto = notificacionMapper.toResponse(notificacionRepository.save(n));
        webSocketService.enviarNotificacion(usuario.getCorreo(), dto);
        return dto;
    }
}
