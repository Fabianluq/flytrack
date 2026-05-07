package com.example.flytrack.services;

import com.example.flytrack.dtos.ReservaRequest;
import com.example.flytrack.dtos.ReservaResponse;
import com.example.flytrack.entities.Reserva;
import com.example.flytrack.entities.Usuario;
import com.example.flytrack.entities.Vuelo;
import com.example.flytrack.mappers.ReservaMapper;
import com.example.flytrack.repositories.ReservaRepository;
import com.example.flytrack.repositories.VueloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final VueloRepository vueloRepository;
    private final ReservaMapper reservaMapper;

    public ReservaService(ReservaRepository reservaRepository,
                          VueloRepository vueloRepository,
                          ReservaMapper reservaMapper) {
        this.reservaRepository = reservaRepository;
        this.vueloRepository = vueloRepository;
        this.reservaMapper = reservaMapper;
    }

    public List<ReservaResponse> listarPorUsuario(Usuario usuario) {
        return reservaRepository.findByUsuario(usuario).stream()
                .map(reservaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<ReservaResponse> listarTodas() {
        return reservaRepository.findAll().stream()
                .map(reservaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ReservaResponse buscarPorCodigo(String codigoReserva) {
        return reservaRepository.findByCodigoReserva(codigoReserva)
                .map(reservaMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada: " + codigoReserva));
    }

    @Transactional
    public ReservaResponse crear(ReservaRequest request, Usuario usuario) {
        Vuelo vuelo = vueloRepository.findById(request.getIdVuelo())
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado con id: " + request.getIdVuelo()));

        String codigoReserva = generarCodigoUnico();

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setVuelo(vuelo);
        reserva.setCodigoReserva(codigoReserva);

        return reservaMapper.toResponse(reservaRepository.save(reserva));
    }

    @Transactional
    public void cancelar(Integer idReserva, Usuario usuario) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + idReserva));
        if (!reserva.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            throw new RuntimeException("No tienes permiso para cancelar esta reserva");
        }
        reserva.setEstado(com.example.flytrack.enums.EstadoReserva.cancelada);
        reservaRepository.save(reserva);
    }

    private String generarCodigoUnico() {
        String codigo;
        do {
            codigo = "FT" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        } while (reservaRepository.existsByCodigoReserva(codigo));
        return codigo;
    }
}
