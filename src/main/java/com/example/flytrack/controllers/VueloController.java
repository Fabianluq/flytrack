package com.example.flytrack.controllers;

import com.example.flytrack.dtos.VueloRequest;
import com.example.flytrack.dtos.VueloResponse;
import com.example.flytrack.enums.EstadoVuelo;
import com.example.flytrack.services.VueloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vuelos")
@Tag(name = "Vuelos", description = "Consulta y gestión de vuelos")
public class VueloController {

    private final VueloService vueloService;

    public VueloController(VueloService vueloService) {
        this.vueloService = vueloService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los vuelos")
    public ResponseEntity<List<VueloResponse>> listar() {
        return ResponseEntity.ok(vueloService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener vuelo por ID")
    public ResponseEntity<VueloResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(vueloService.buscarPorId(id));
    }

    @GetMapping("/numero/{numeroVuelo}")
    @Operation(summary = "Buscar vuelo por número de vuelo")
    public ResponseEntity<VueloResponse> buscarPorNumero(@PathVariable String numeroVuelo) {
        return ResponseEntity.ok(vueloService.buscarPorNumero(numeroVuelo));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar vuelos por estado")
    public ResponseEntity<List<VueloResponse>> listarPorEstado(@PathVariable EstadoVuelo estado) {
        return ResponseEntity.ok(vueloService.listarPorEstado(estado));
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Crear vuelo")
    public ResponseEntity<VueloResponse> crear(@Valid @RequestBody VueloRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vueloService.crear(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Actualizar vuelo (cambia estado → notifica pasajeros automáticamente)")
    public ResponseEntity<VueloResponse> actualizar(@PathVariable Integer id,
                                                      @Valid @RequestBody VueloRequest request) {
        return ResponseEntity.ok(vueloService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Eliminar vuelo")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        vueloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
