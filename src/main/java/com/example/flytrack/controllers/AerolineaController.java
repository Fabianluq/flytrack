package com.example.flytrack.controllers;

import com.example.flytrack.entities.Aerolinea;
import com.example.flytrack.services.AerolineaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aerolineas")
@RequiredArgsConstructor
public class AerolineaController {

    private final AerolineaService aerolineaService;

    @GetMapping
    public List<Aerolinea> listarTodos() {
        return aerolineaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aerolinea> buscarPorId(@PathVariable Integer id) {
        return aerolineaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Aerolinea guardar(@RequestBody Aerolinea aerolinea) {
        return aerolineaService.guardar(aerolinea);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        aerolineaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
