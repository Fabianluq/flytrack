package com.example.flytrack.dtos;

import com.example.flytrack.enums.TipoNotificacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionResponse {
    private Integer idNotificacion;
    private String titulo;
    private String mensaje;
    private TipoNotificacion tipo;
    private Boolean leida;
    private LocalDateTime fechaEnvio;
    private Integer idVuelo;
    private String numeroVuelo;
}
