package com.example.flytrack.mappers;

import com.example.flytrack.dtos.UsuarioResponse;
import com.example.flytrack.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    private final RolMapper rolMapper;

    public UsuarioMapper(RolMapper rolMapper) {
        this.rolMapper = rolMapper;
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        if (usuario == null) return null;
        UsuarioResponse dto = new UsuarioResponse();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setCorreo(usuario.getCorreo());
        dto.setTelefono(usuario.getTelefono());
        dto.setFechaRegistro(usuario.getFechaRegistro());
        dto.setEstado(usuario.getEstado());
        dto.setRol(rolMapper.toResponse(usuario.getRol()));
        return dto;
    }
}
