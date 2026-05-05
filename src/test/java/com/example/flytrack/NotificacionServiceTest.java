package com.example.flytrack;

import com.example.flytrack.entities.Notificacion;
import com.example.flytrack.repositories.NotificacionRepository;
import com.example.flytrack.services.NotificacionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    @Test
    void listarTodos_debeRetornarListaDeNotificaciones() {
        Notificacion n1 = new Notificacion();
        n1.setIdNotificacion(1);
        n1.setTitulo("Vuelo retrasado");
        n1.setLeida(false);

        Notificacion n2 = new Notificacion();
        n2.setIdNotificacion(2);
        n2.setTitulo("Puerta de embarque cambiada");
        n2.setLeida(true);

        when(notificacionRepository.findAll()).thenReturn(Arrays.asList(n1, n2));

        List<Notificacion> resultado = notificacionService.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Vuelo retrasado", resultado.get(0).getTitulo());
        verify(notificacionRepository, times(1)).findAll();
    }

    @Test
    void buscarNoLeidasPorUsuario_debeRetornarSoloNoLeidas() {
        Notificacion n1 = new Notificacion();
        n1.setTitulo("Vuelo retrasado");
        n1.setLeida(false);

        when(notificacionRepository.findByUsuarioIdUsuarioAndLeidaFalse(1))
                .thenReturn(Arrays.asList(n1));

        List<Notificacion> resultado = notificacionService.buscarNoLeidasPorUsuario(1);

        assertEquals(1, resultado.size());
        assertFalse(resultado.get(0).getLeida());
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarNotificacion() {
        Notificacion notificacion = new Notificacion();
        notificacion.setIdNotificacion(1);
        notificacion.setTitulo("Vuelo retrasado");

        when(notificacionRepository.findById(1)).thenReturn(Optional.of(notificacion));

        Optional<Notificacion> resultado = notificacionService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Vuelo retrasado", resultado.get().getTitulo());
    }

    @Test
    void guardar_debeRetornarNotificacionGuardada() {
        Notificacion notificacion = new Notificacion();
        notificacion.setTitulo("Vuelo retrasado");

        when(notificacionRepository.save(notificacion)).thenReturn(notificacion);

        Notificacion resultado = notificacionService.guardar(notificacion);

        assertNotNull(resultado);
        assertEquals("Vuelo retrasado", resultado.getTitulo());
    }

    @Test
    void eliminar_debeEjecutarDelete() {
        notificacionService.eliminar(1);
        verify(notificacionRepository, times(1)).deleteById(1);
    }
}
