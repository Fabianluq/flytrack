package com.example.flytrack.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AeropuertoRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El código IATA es obligatorio")
    @Size(min = 3, max = 3, message = "El código IATA debe tener exactamente 3 caracteres")
    private String codigoIata;

    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;

    @NotBlank(message = "El país es obligatorio")
    private String pais;
}
