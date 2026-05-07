package com.example.flytrack.mappers;

import com.example.flytrack.dtos.NotificacionResponse;
import com.example.flytrack.entities.Notificacion;
import org.springframework.stereotype.Component;

@Component
public class NotificacionMapper {
    public NotificacionResponse toResponse(Notificacion n) {
        if (n == null) return null;
        NotificacionResponse dto = new NotificacionResponse();
        dto.setIdNotificacion(n.getIdNotificacion());
        dto.setTitulo(n.getTitulo());
        dto.setMensaje(n.getMensaje());
        dto.setTipo(n.getTipo());
        dto.setLeida(n.getLeida());
        dto.setFechaEnvio(n.getFechaEnvio());
        dto.setIdVuelo(n.getVuelo().getIdVuelo());
        dto.setNumeroVuelo(n.getVuelo().getNumeroVuelo());
        return dto;
    }
}
