package com.example.flytrack;

import com.example.flytrack.repositories.*;
import com.example.flytrack.security.CustomUserDetailsService;
import com.example.flytrack.security.JwtAuthFilter;
import com.example.flytrack.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Test de arranque del contexto de Spring.
 * Usa @MockBean para todas las dependencias de infraestructura
 * (BD, mail, JWT) que no están disponibles en CI/CD sin servidor real.
 */
@SpringBootTest
class FlytrackApplicationTests {

    // ── Repositorios ─────────────────────────────────────────────────────────
    @MockBean RolRepository rolRepository;
    @MockBean UsuarioRepository usuarioRepository;
    @MockBean AerolineaRepository aerolineaRepository;
    @MockBean AeropuertoRepository aeropuertoRepository;
    @MockBean VueloRepository vueloRepository;
    @MockBean ReservaRepository reservaRepository;
    @MockBean EquipajeRepository equipajeRepository;
    @MockBean NotificacionRepository notificacionRepository;

    // ── Infraestructura ───────────────────────────────────────────────────────
    @MockBean JavaMailSender javaMailSender;

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring arranca correctamente
    }
}
