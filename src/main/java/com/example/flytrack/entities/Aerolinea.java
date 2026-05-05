package com.example.flytrack.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "aerolinea")
public class Aerolinea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aerolinea")
    private Integer idAerolinea;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "codigo_iata", nullable = false, unique = true, length = 3)
    private String codigoIata;

    @Column(name = "pais", nullable = false, length = 100)
    private String pais;
}
