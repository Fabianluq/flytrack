package com.example.flytrack.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReporteIncidenteRequest {

    @NotBlank(message = "La descripción del incidente es obligatoria")
    private String reporteIncidente;
}
