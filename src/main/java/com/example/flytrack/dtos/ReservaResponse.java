package com.example.flytrack.dtos;

import com.example.flytrack.enums.EstadoReserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaResponse {
    private Integer idReserva;
    private String codigoReserva;
    private LocalDateTime fechaReserva;
    private EstadoReserva estado;
    private UsuarioResponse usuario;
    private VueloResponse vuelo;
}
