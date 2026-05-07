package com.example.flytrack.controllers;

import com.example.flytrack.dtos.AerolineaRequest;
import com.example.flytrack.dtos.AerolineaResponse;
import com.example.flytrack.services.AerolineaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aerolineas")
@Tag(name = "Aerolíneas", description = "Gestión de aerolíneas")
public class AerolineaController {

    private final AerolineaService aerolineaService;

    public AerolineaController(AerolineaService aerolineaService) {
        this.aerolineaService = aerolineaService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las aerolíneas")
    public ResponseEntity<List<AerolineaResponse>> listar() {
        return ResponseEntity.ok(aerolineaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener aerolínea por ID")
    public ResponseEntity<AerolineaResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(aerolineaService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Crear aerolínea")
    public ResponseEntity<AerolineaResponse> crear(@Valid @RequestBody AerolineaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(aerolineaService.crear(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Actualizar aerolínea")
    public ResponseEntity<AerolineaResponse> actualizar(@PathVariable Integer id,
                                                          @Valid @RequestBody AerolineaRequest request) {
        return ResponseEntity.ok(aerolineaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Eliminar aerolínea")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        aerolineaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
