package com.example.flytrack.mappers;

import com.example.flytrack.dtos.VueloResponse;
import com.example.flytrack.entities.Vuelo;
import org.springframework.stereotype.Component;

@Component
public class VueloMapper {

    private final AerolineaMapper aerolineaMapper;
    private final AeropuertoMapper aeropuertoMapper;

    public VueloMapper(AerolineaMapper aerolineaMapper, AeropuertoMapper aeropuertoMapper) {
        this.aerolineaMapper = aerolineaMapper;
        this.aeropuertoMapper = aeropuertoMapper;
    }

    public VueloResponse toResponse(Vuelo v) {
        if (v == null) return null;
        VueloResponse dto = new VueloResponse();
        dto.setIdVuelo(v.getIdVuelo());
        dto.setNumeroVuelo(v.getNumeroVuelo());
        dto.setAerolinea(aerolineaMapper.toResponse(v.getAerolinea()));
        dto.setOrigen(aeropuertoMapper.toResponse(v.getOrigen()));
        dto.setDestino(aeropuertoMapper.toResponse(v.getDestino()));
        dto.setFechaSalida(v.getFechaSalida());
        dto.setFechaLlegada(v.getFechaLlegada());
        dto.setPuertaEmbarque(v.getPuertaEmbarque());
        dto.setEstado(v.getEstado());
        return dto;
    }
}
