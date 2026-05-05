package com.example.flytrack.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "vuelo")
public class Vuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vuelo")
    private Integer idVuelo;

    @ManyToOne
    @JoinColumn(name = "id_aerolinea", nullable = false)
    private Aerolinea aerolinea;

    @ManyToOne
    @JoinColumn(name = "id_origen", nullable = false)
    private Aeropuerto origen;

    @ManyToOne
    @JoinColumn(name = "id_destino", nullable = false)
    private Aeropuerto destino;

    @Column(name = "numero_vuelo", nullable = false, length = 10)
    private String numeroVuelo;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDateTime fechaSalida;

    @Column(name = "fecha_llegada", nullable = false)
    private LocalDateTime fechaLlegada;

    @Column(name = "puerta_embarque", length = 10)
    private String puertaEmbarque;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoVuelo estado;

    public enum EstadoVuelo {
        programado, embarcando, en_vuelo, aterrizado, cancelado
    }
}
