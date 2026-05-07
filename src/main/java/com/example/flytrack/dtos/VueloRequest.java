package com.example.flytrack.dtos;

import com.example.flytrack.enums.EstadoVuelo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VueloRequest {

    @NotNull(message = "La aerolínea es obligatoria")
    private Integer idAerolinea;

    @NotNull(message = "El aeropuerto de origen es obligatorio")
    private Integer idOrigen;

    @NotNull(message = "El aeropuerto de destino es obligatorio")
    private Integer idDestino;

    @NotBlank(message = "El número de vuelo es obligatorio")
    private String numeroVuelo;

    @NotNull(message = "La fecha de salida es obligatoria")
    private LocalDateTime fechaSalida;

    @NotNull(message = "La fecha de llegada es obligatoria")
    private LocalDateTime fechaLlegada;

    private String puertaEmbarque;

    private EstadoVuelo estado;
}
