package com.example.flytrack.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "aeropuerto")
public class Aeropuerto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aeropuerto")
    private Integer idAeropuerto;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "codigo_iata", nullable = false, unique = true, length = 3)
    private String codigoIata;

    @Column(name = "ciudad", nullable = false, length = 100)
    private String ciudad;

    @Column(name = "pais", nullable = false, length = 100)
    private String pais;
}
