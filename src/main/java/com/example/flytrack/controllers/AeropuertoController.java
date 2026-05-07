package com.example.flytrack.controllers;

import com.example.flytrack.dtos.AeropuertoRequest;
import com.example.flytrack.dtos.AeropuertoResponse;
import com.example.flytrack.services.AeropuertoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aeropuertos")
@Tag(name = "Aeropuertos", description = "Gestión de aeropuertos")
public class AeropuertoController {

    private final AeropuertoService aeropuertoService;

    public AeropuertoController(AeropuertoService aeropuertoService) {
        this.aeropuertoService = aeropuertoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los aeropuertos")
    public ResponseEntity<List<AeropuertoResponse>> listar() {
        return ResponseEntity.ok(aeropuertoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener aeropuerto por ID")
    public ResponseEntity<AeropuertoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(aeropuertoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Crear aeropuerto")
    public ResponseEntity<AeropuertoResponse> crear(@Valid @RequestBody AeropuertoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(aeropuertoService.crear(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Actualizar aeropuerto")
    public ResponseEntity<AeropuertoResponse> actualizar(@PathVariable Integer id,
                                                           @Valid @RequestBody AeropuertoRequest request) {
        return ResponseEntity.ok(aeropuertoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Eliminar aeropuerto")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        aeropuertoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
