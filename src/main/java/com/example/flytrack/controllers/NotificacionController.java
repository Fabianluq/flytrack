package com.example.flytrack.controllers;

import com.example.flytrack.entities.Notificacion;
import com.example.flytrack.services.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @GetMapping
    public List<Notificacion> listarTodos() {
        return notificacionService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> buscarPorId(@PathVariable Integer id) {
        return notificacionService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Notificacion> buscarPorUsuario(@PathVariable Integer idUsuario) {
        return notificacionService.buscarPorUsuario(idUsuario);
    }

    @GetMapping("/usuario/{idUsuario}/noleidas")
    public List<Notificacion> buscarNoLeidas(@PathVariable Integer idUsuario) {
        return notificacionService.buscarNoLeidasPorUsuario(idUsuario);
    }

    @PostMapping
    public Notificacion guardar(@RequestBody Notificacion notificacion) {
        return notificacionService.guardar(notificacion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
