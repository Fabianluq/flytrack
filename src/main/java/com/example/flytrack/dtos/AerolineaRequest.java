package com.example.flytrack.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AerolineaRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El código IATA es obligatorio")
    @Size(min = 2, max = 3, message = "El código IATA debe tener 2 o 3 caracteres")
    private String codigoIata;

    @NotBlank(message = "El país es obligatorio")
    private String pais;
}
