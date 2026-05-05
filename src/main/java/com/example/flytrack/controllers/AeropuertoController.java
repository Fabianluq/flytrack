package com.example.flytrack.controllers;

import com.example.flytrack.entities.Aeropuerto;
import com.example.flytrack.services.AeropuertoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aeropuertos")
@RequiredArgsConstructor
public class AeropuertoController {

    private final AeropuertoService aeropuertoService;

    @GetMapping
    public List<Aeropuerto> listarTodos() {
        return aeropuertoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aeropuerto> buscarPorId(@PathVariable Integer id) {
        return aeropuertoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Aeropuerto guardar(@RequestBody Aeropuerto aeropuerto) {
        return aeropuertoService.guardar(aeropuerto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        aeropuertoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
