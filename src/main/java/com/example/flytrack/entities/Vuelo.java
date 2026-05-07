package com.example.flytrack.entities;

import com.example.flytrack.enums.EstadoVuelo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "vuelo")
@Data
@NoArgsConstructor
public class Vuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vuelo")
    private Integer idVuelo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_aerolinea", nullable = false)
    private Aerolinea aerolinea;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_origen", nullable = false)
    private Aeropuerto origen;

    @ManyToOne(fetch = FetchType.EAGER)
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
    @Column(name = "estado", nullable = false)
    private EstadoVuelo estado;

    @PrePersist
    public void prePersist() {
        if (this.estado == null) {
            this.estado = EstadoVuelo.programado;
        }
    }
}
