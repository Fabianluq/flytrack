package com.example.flytrack.entities;

import com.example.flytrack.enums.EstadoEquipaje;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipaje")
@Data
@NoArgsConstructor
public class Equipaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipaje")
    private Integer idEquipaje;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reserva reserva;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "peso_kg", nullable = false, precision = 5, scale = 2)
    private BigDecimal pesoKg;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoEquipaje estado;

    // Reporte de incidente embebido en la misma tabla
    @Column(name = "reporte_incidente", columnDefinition = "TEXT")
    private String reporteIncidente;

    @Column(name = "fecha_reporte")
    private LocalDateTime fechaReporte;

    @PrePersist
    public void prePersist() {
        if (this.estado == null) {
            this.estado = EstadoEquipaje.registrado;
        }
    }
}
