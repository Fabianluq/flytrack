package com.example.flytrack.mappers;

import com.example.flytrack.dtos.AeropuertoResponse;
import com.example.flytrack.entities.Aeropuerto;
import org.springframework.stereotype.Component;

@Component
public class AeropuertoMapper {
    public AeropuertoResponse toResponse(Aeropuerto a) {
        if (a == null) return null;
        return new AeropuertoResponse(
                a.getIdAeropuerto(), a.getNombre(), a.getCodigoIata(), a.getCiudad(), a.getPais());
    }
}
