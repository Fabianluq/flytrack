package com.example.flytrack.mappers;

import com.example.flytrack.dtos.RolResponse;
import com.example.flytrack.entities.Rol;
import org.springframework.stereotype.Component;

@Component
public class RolMapper {
    public RolResponse toResponse(Rol rol) {
        if (rol == null) return null;
        return new RolResponse(rol.getIdRol(), rol.getDescripcion());
    }
}
