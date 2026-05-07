package com.example.flytrack.services;

import com.example.flytrack.dtos.EquipajeResponse;
import com.example.flytrack.dtos.ReporteIncidenteRequest;
import com.example.flytrack.entities.Equipaje;
import com.example.flytrack.entities.Reserva;
import com.example.flytrack.entities.Usuario;
import com.example.flytrack.enums.EstadoEquipaje;
import com.example.flytrack.mappers.EquipajeMapper;
import com.example.flytrack.repositories.EquipajeRepository;
import com.example.flytrack.repositories.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipajeService {

    private final EquipajeRepository equipajeRepository;
    private final ReservaRepository reservaRepository;
    private final EquipajeMapper equipajeMapper;

    public EquipajeService(EquipajeRepository equipajeRepository,
                           ReservaRepository reservaRepository,
                           EquipajeMapper equipajeMapper) {
        this.equipajeRepository = equipajeRepository;
        this.reservaRepository = reservaRepository;
        this.equipajeMapper = equipajeMapper;
    }

    public List<EquipajeResponse> listarPorReserva(Integer idReserva, Usuario usuario) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + idReserva));
        // Pasajero solo puede ver su propio equipaje
        if (!reserva.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())
                && !usuario.getRol().getDescripcion().equals("admin")) {
            throw new RuntimeException("No tienes permiso para ver este equipaje");
        }
        return equipajeRepository.findByReserva(reserva).stream()
                .map(equipajeMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<EquipajeResponse> listarTodos() {
        return equipajeRepository.findAll().stream()
                .map(equipajeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public EquipajeResponse reportarIncidente(Integer idEquipaje,
                                               ReporteIncidenteRequest request,
                                               Usuario usuario) {
        Equipaje equipaje = equipajeRepository.findById(idEquipaje)
                .orElseThrow(() -> new RuntimeException("Equipaje no encontrado con id: " + idEquipaje));

        // El pasajero solo puede reportar su propio equipaje
        if (!equipaje.getReserva().getUsuario().getIdUsuario().equals(usuario.getIdUsuario())
                && !usuario.getRol().getDescripcion().equals("admin")) {
            throw new RuntimeException("No tienes permiso para reportar este equipaje");
        }

        equipaje.setReporteIncidente(request.getReporteIncidente());
        equipaje.setFechaReporte(LocalDateTime.now());
        equipaje.setEstado(EstadoEquipaje.danado); // marcar como dañado al reportar

        return equipajeMapper.toResponse(equipajeRepository.save(equipaje));
    }

    @Transactional
    public EquipajeResponse actualizarEstado(Integer idEquipaje, EstadoEquipaje nuevoEstado) {
        Equipaje equipaje = equipajeRepository.findById(idEquipaje)
                .orElseThrow(() -> new RuntimeException("Equipaje no encontrado con id: " + idEquipaje));
        equipaje.setEstado(nuevoEstado);
        return equipajeMapper.toResponse(equipajeRepository.save(equipaje));
    }
}
