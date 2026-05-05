package com.example.flytrack.controllers;

import com.example.flytrack.entities.Equipaje;
import com.example.flytrack.services.EquipajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipajes")
@RequiredArgsConstructor
public class EquipajeController {

    private final EquipajeService equipajeService;

    @GetMapping
    public List<Equipaje> listarTodos() {
        return equipajeService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipaje> buscarPorId(@PathVariable Integer id) {
        return equipajeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/reserva/{idReserva}")
    public List<Equipaje> buscarPorReserva(@PathVariable Integer idReserva) {
        return equipajeService.buscarPorReserva(idReserva);
    }

    @PostMapping
    public Equipaje guardar(@RequestBody Equipaje equipaje) {
        return equipajeService.guardar(equipaje);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        equipajeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
