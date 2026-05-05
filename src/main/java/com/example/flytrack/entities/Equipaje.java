package com.example.flytrack.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "equipaje")
public class Equipaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipaje")
    private Integer idEquipaje;

    @ManyToOne
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reserva reserva;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "peso_kg", nullable = false)
    private Float pesoKg;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoEquipaje estado;

    @Column(name = "reporte_incidente", columnDefinition = "TEXT")
    private String reporteIncidente;

    @Column(name = "fecha_reporte")
    private LocalDateTime fechaReporte;

    public enum EstadoEquipaje {
        registrado, en_bodega, entregado, perdido, danado
    }

}
