package com.example.flytrack.controllers;

import com.example.flytrack.entities.Vuelo;
import com.example.flytrack.services.VueloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vuelos")
@RequiredArgsConstructor
public class VueloController {

    private final VueloService vueloService;

    @GetMapping
    public List<Vuelo> listarTodos() {
        return vueloService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vuelo> buscarPorId(@PathVariable Integer id) {
        return vueloService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public List<Vuelo> buscarPorEstado(@PathVariable Vuelo.EstadoVuelo estado) {
        return vueloService.buscarPorEstado(estado);
    }

    @PostMapping
    public Vuelo guardar(@RequestBody Vuelo vuelo) {
        return vueloService.guardar(vuelo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        vueloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
