package com.example.flytrack.controllers;

import com.example.flytrack.dtos.NotificacionResponse;
import com.example.flytrack.services.WebSocketNotificacionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Tag(name = "WebSocket", description = "Endpoints STOMP para notificaciones en tiempo real")
public class WebSocketController {

    private final WebSocketNotificacionService webSocketService;

    public WebSocketController(WebSocketNotificacionService webSocketService) {
        this.webSocketService = webSocketService;
    }

    /**
     * El cliente puede enviar un ping a /app/ping y todos los suscritos a /topic/vuelos lo reciben.
     * Útil para mantener la conexión viva o sincronizar el tablero de vuelos.
     */
    @MessageMapping("/ping")
    @SendTo("/topic/vuelos")
    public String ping(String mensaje) {
        return "pong: " + mensaje;
    }
}
