package com.example.flytrack.services;

import com.example.flytrack.dtos.NotificacionResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketNotificacionService {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketNotificacionService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Envía una notificación en tiempo real al usuario identificado por su correo.
     * El cliente debe suscribirse a /user/queue/notificaciones
     */
    public void enviarNotificacion(String correoUsuario, NotificacionResponse notificacion) {
        messagingTemplate.convertAndSendToUser(
                correoUsuario,
                "/queue/notificaciones",
                notificacion
        );
    }

    /**
     * Envía un mensaje de broadcast a todos los clientes suscritos a /topic/vuelos
     * (por ejemplo, cuando se actualiza el tablero de vuelos).
     */
    public void broadcastActualizacionVuelo(Object payload) {
        messagingTemplate.convertAndSend("/topic/vuelos", payload);
    }
}
