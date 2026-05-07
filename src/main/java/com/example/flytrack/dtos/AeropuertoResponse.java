package com.example.flytrack.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AeropuertoResponse {
    private Integer idAeropuerto;
    private String nombre;
    private String codigoIata;
    private String ciudad;
    private String pais;
}
