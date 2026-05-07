package com.example.flytrack.dtos;

import com.example.flytrack.enums.EstadoVuelo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VueloResponse {
    private Integer idVuelo;
    private String numeroVuelo;
    private AerolineaResponse aerolinea;
    private AeropuertoResponse origen;
    private AeropuertoResponse destino;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaLlegada;
    private String puertaEmbarque;
    private EstadoVuelo estado;
}
