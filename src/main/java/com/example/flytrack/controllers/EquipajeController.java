package com.example.flytrack.controllers;

import com.example.flytrack.dtos.EquipajeResponse;
import com.example.flytrack.dtos.ReporteIncidenteRequest;
import com.example.flytrack.entities.Usuario;
import com.example.flytrack.enums.EstadoEquipaje;
import com.example.flytrack.services.EquipajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipaje")
@Tag(name = "Equipaje", description = "Consulta y reporte de equipaje")
public class EquipajeController {

    private final EquipajeService equipajeService;

    public EquipajeController(EquipajeService equipajeService) {
        this.equipajeService = equipajeService;
    }

    @GetMapping("/reserva/{idReserva}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Ver equipaje de una reserva")
    public ResponseEntity<List<EquipajeResponse>> listarPorReserva(
            @PathVariable Integer idReserva,
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(equipajeService.listarPorReserva(idReserva, usuario));
    }

    @GetMapping
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Listar todo el equipaje (solo admin)")
    public ResponseEntity<List<EquipajeResponse>> listarTodo() {
        return ResponseEntity.ok(equipajeService.listarTodos());
    }

    @PostMapping("/{idEquipaje}/reportar-incidente")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Reportar incidente con el equipaje")
    public ResponseEntity<EquipajeResponse> reportarIncidente(
            @PathVariable Integer idEquipaje,
            @Valid @RequestBody ReporteIncidenteRequest request,
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(equipajeService.reportarIncidente(idEquipaje, request, usuario));
    }

    @PatchMapping("/{idEquipaje}/estado")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Actualizar estado del equipaje (solo admin)")
    public ResponseEntity<EquipajeResponse> actualizarEstado(
            @PathVariable Integer idEquipaje,
            @RequestParam EstadoEquipaje estado) {
        return ResponseEntity.ok(equipajeService.actualizarEstado(idEquipaje, estado));
    }
}
