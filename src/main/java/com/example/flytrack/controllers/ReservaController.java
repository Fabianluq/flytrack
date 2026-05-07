package com.example.flytrack.controllers;

import com.example.flytrack.dtos.ReservaRequest;
import com.example.flytrack.dtos.ReservaResponse;
import com.example.flytrack.entities.Usuario;
import com.example.flytrack.services.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
@Tag(name = "Reservas", description = "Gestión de reservas de vuelo")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping("/mis-reservas")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Ver mis reservas (pasajero autenticado)")
    public ResponseEntity<List<ReservaResponse>> misReservas(
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(reservaService.listarPorUsuario(usuario));
    }

    @GetMapping
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Listar todas las reservas (solo admin)")
    public ResponseEntity<List<ReservaResponse>> listarTodas() {
        return ResponseEntity.ok(reservaService.listarTodas());
    }

    @GetMapping("/codigo/{codigo}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Buscar reserva por código")
    public ResponseEntity<ReservaResponse> buscarPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(reservaService.buscarPorCodigo(codigo));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('pasajero', 'admin')")
    @Operation(summary = "Crear reserva")
    public ResponseEntity<ReservaResponse> crear(
            @Valid @RequestBody ReservaRequest request,
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservaService.crear(request, usuario));
    }

    @PatchMapping("/{id}/cancelar")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Cancelar reserva")
    public ResponseEntity<Void> cancelar(
            @PathVariable Integer id,
            @AuthenticationPrincipal Usuario usuario) {
        reservaService.cancelar(id, usuario);
        return ResponseEntity.noContent().build();
    }
}
