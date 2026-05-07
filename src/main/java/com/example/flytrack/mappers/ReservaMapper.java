package com.example.flytrack.mappers;

import com.example.flytrack.dtos.ReservaResponse;
import com.example.flytrack.entities.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {

    private final UsuarioMapper usuarioMapper;
    private final VueloMapper vueloMapper;

    public ReservaMapper(UsuarioMapper usuarioMapper, VueloMapper vueloMapper) {
        this.usuarioMapper = usuarioMapper;
        this.vueloMapper = vueloMapper;
    }

    public ReservaResponse toResponse(Reserva r) {
        if (r == null) return null;
        ReservaResponse dto = new ReservaResponse();
        dto.setIdReserva(r.getIdReserva());
        dto.setCodigoReserva(r.getCodigoReserva());
        dto.setFechaReserva(r.getFechaReserva());
        dto.setEstado(r.getEstado());
        dto.setUsuario(usuarioMapper.toResponse(r.getUsuario()));
        dto.setVuelo(vueloMapper.toResponse(r.getVuelo()));
        return dto;
    }
}
