package com.example.flytrack.controllers;

import com.example.flytrack.dtos.NotificacionResponse;
import com.example.flytrack.entities.Usuario;
import com.example.flytrack.services.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notificaciones")
@PreAuthorize("isAuthenticated()")
@Tag(name = "Notificaciones", description = "Notificaciones del pasajero")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping
    @Operation(summary = "Ver todas mis notificaciones")
    public ResponseEntity<List<NotificacionResponse>> misNotificaciones(
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(notificacionService.listarPorUsuario(usuario));
    }

    @GetMapping("/no-leidas")
    @Operation(summary = "Ver notificaciones no leídas")
    public ResponseEntity<List<NotificacionResponse>> noLeidas(
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(notificacionService.listarNoLeidas(usuario));
    }

    @GetMapping("/no-leidas/count")
    @Operation(summary = "Contar notificaciones no leídas")
    public ResponseEntity<Map<String, Long>> contarNoLeidas(
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(Map.of("total", notificacionService.contarNoLeidas(usuario)));
    }

    @PatchMapping("/{id}/leer")
    @Operation(summary = "Marcar notificación como leída")
    public ResponseEntity<Void> marcarLeida(
            @PathVariable Integer id,
            @AuthenticationPrincipal Usuario usuario) {
        notificacionService.marcarComoLeida(id, usuario);
        return ResponseEntity.noContent().build();
    }
}
