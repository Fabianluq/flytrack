package com.example.flytrack.mappers;

import com.example.flytrack.dtos.AerolineaResponse;
import com.example.flytrack.entities.Aerolinea;
import org.springframework.stereotype.Component;

@Component
public class AerolineaMapper {
    public AerolineaResponse toResponse(Aerolinea a) {
        if (a == null) return null;
        return new AerolineaResponse(a.getIdAerolinea(), a.getNombre(), a.getCodigoIata(), a.getPais());
    }
}
