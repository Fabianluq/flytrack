package com.example.flytrack.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservaRequest {

    @NotNull(message = "El id del vuelo es obligatorio")
    private Integer idVuelo;
}
