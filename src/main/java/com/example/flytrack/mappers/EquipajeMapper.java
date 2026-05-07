package com.example.flytrack.mappers;

import com.example.flytrack.dtos.EquipajeResponse;
import com.example.flytrack.entities.Equipaje;
import org.springframework.stereotype.Component;

@Component
public class EquipajeMapper {
    public EquipajeResponse toResponse(Equipaje e) {
        if (e == null) return null;
        EquipajeResponse dto = new EquipajeResponse();
        dto.setIdEquipaje(e.getIdEquipaje());
        dto.setIdReserva(e.getReserva().getIdReserva());
        dto.setCodigoReserva(e.getReserva().getCodigoReserva());
        dto.setDescripcion(e.getDescripcion());
        dto.setPesoKg(e.getPesoKg());
        dto.setEstado(e.getEstado());
        dto.setReporteIncidente(e.getReporteIncidente());
        dto.setFechaReporte(e.getFechaReporte());
        return dto;
    }
}
