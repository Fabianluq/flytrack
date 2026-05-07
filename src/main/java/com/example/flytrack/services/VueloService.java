package com.example.flytrack.services;

import com.example.flytrack.dtos.VueloRequest;
import com.example.flytrack.dtos.VueloResponse;
import com.example.flytrack.entities.Aerolinea;
import com.example.flytrack.entities.Aeropuerto;
import com.example.flytrack.entities.Vuelo;
import com.example.flytrack.enums.EstadoVuelo;
import com.example.flytrack.mappers.VueloMapper;
import com.example.flytrack.repositories.AerolineaRepository;
import com.example.flytrack.repositories.AeropuertoRepository;
import com.example.flytrack.repositories.VueloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VueloService {

    private final VueloRepository vueloRepository;
    private final AerolineaRepository aerolineaRepository;
    private final AeropuertoRepository aeropuertoRepository;
    private final VueloMapper vueloMapper;
    private final NotificacionService notificacionService;

    public VueloService(VueloRepository vueloRepository,
                        AerolineaRepository aerolineaRepository,
                        AeropuertoRepository aeropuertoRepository,
                        VueloMapper vueloMapper,
                        NotificacionService notificacionService) {
        this.vueloRepository = vueloRepository;
        this.aerolineaRepository = aerolineaRepository;
        this.aeropuertoRepository = aeropuertoRepository;
        this.vueloMapper = vueloMapper;
        this.notificacionService = notificacionService;
    }

    public List<VueloResponse> listarTodos() {
        return vueloRepository.findAll().stream()
                .map(vueloMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<VueloResponse> listarPorEstado(EstadoVuelo estado) {
        return vueloRepository.findByEstado(estado).stream()
                .map(vueloMapper::toResponse)
                .collect(Collectors.toList());
    }

    public VueloResponse buscarPorId(Integer id) {
        return vueloRepository.findById(id)
                .map(vueloMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado con id: " + id));
    }

    public VueloResponse buscarPorNumero(String numeroVuelo) {
        return vueloRepository.findByNumeroVuelo(numeroVuelo)
                .map(vueloMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado: " + numeroVuelo));
    }

    @Transactional
    public VueloResponse crear(VueloRequest request) {
        Aerolinea aerolinea = aerolineaRepository.findById(request.getIdAerolinea())
                .orElseThrow(() -> new RuntimeException("Aerolínea no encontrada"));
        Aeropuerto origen = aeropuertoRepository.findById(request.getIdOrigen())
                .orElseThrow(() -> new RuntimeException("Aeropuerto de origen no encontrado"));
        Aeropuerto destino = aeropuertoRepository.findById(request.getIdDestino())
                .orElseThrow(() -> new RuntimeException("Aeropuerto de destino no encontrado"));

        Vuelo vuelo = new Vuelo();
        vuelo.setAerolinea(aerolinea);
        vuelo.setOrigen(origen);
        vuelo.setDestino(destino);
        vuelo.setNumeroVuelo(request.getNumeroVuelo().toUpperCase());
        vuelo.setFechaSalida(request.getFechaSalida());
        vuelo.setFechaLlegada(request.getFechaLlegada());
        vuelo.setPuertaEmbarque(request.getPuertaEmbarque());
        if (request.getEstado() != null) vuelo.setEstado(request.getEstado());

        return vueloMapper.toResponse(vueloRepository.save(vuelo));
    }

    @Transactional
    public VueloResponse actualizar(Integer id, VueloRequest request) {
        Vuelo vuelo = vueloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado con id: " + id));

        EstadoVuelo estadoAnterior = vuelo.getEstado();

        if (request.getIdAerolinea() != null) {
            vuelo.setAerolinea(aerolineaRepository.findById(request.getIdAerolinea())
                    .orElseThrow(() -> new RuntimeException("Aerolínea no encontrada")));
        }
        if (request.getIdOrigen() != null) {
            vuelo.setOrigen(aeropuertoRepository.findById(request.getIdOrigen())
                    .orElseThrow(() -> new RuntimeException("Aeropuerto de origen no encontrado")));
        }
        if (request.getIdDestino() != null) {
            vuelo.setDestino(aeropuertoRepository.findById(request.getIdDestino())
                    .orElseThrow(() -> new RuntimeException("Aeropuerto de destino no encontrado")));
        }
        if (request.getNumeroVuelo() != null) vuelo.setNumeroVuelo(request.getNumeroVuelo().toUpperCase());
        if (request.getFechaSalida() != null) vuelo.setFechaSalida(request.getFechaSalida());
        if (request.getFechaLlegada() != null) vuelo.setFechaLlegada(request.getFechaLlegada());
        if (request.getPuertaEmbarque() != null) vuelo.setPuertaEmbarque(request.getPuertaEmbarque());
        if (request.getEstado() != null) vuelo.setEstado(request.getEstado());

        Vuelo actualizado = vueloRepository.save(vuelo);

        // Si el estado cambió → notificar a todos los pasajeros con reserva en este vuelo
        if (request.getEstado() != null && !request.getEstado().equals(estadoAnterior)) {
            notificacionService.notificarCambioEstadoVuelo(actualizado);
        }

        return vueloMapper.toResponse(actualizado);
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!vueloRepository.existsById(id)) {
            throw new RuntimeException("Vuelo no encontrado con id: " + id);
        }
        vueloRepository.deleteById(id);
    }
}
