package com.example.flytrack.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AerolineaResponse {
    private Integer idAerolinea;
    private String nombre;
    private String codigoIata;
    private String pais;
}
