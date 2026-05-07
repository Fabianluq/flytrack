package com.example.flytrack.dtos;

import com.example.flytrack.enums.EstadoEquipaje;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipajeResponse {
    private Integer idEquipaje;
    private Integer idReserva;
    private String codigoReserva;
    private String descripcion;
    private BigDecimal pesoKg;
    private EstadoEquipaje estado;
    private String reporteIncidente;
    private LocalDateTime fechaReporte;
}
