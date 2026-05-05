package com.example.flytrack;

import com.example.flytrack.entities.Reserva;
import com.example.flytrack.repositories.ReservaRepository;
import com.example.flytrack.services.ReservaService;
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

public class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    void buscarPorUsuario_debeRetornarReservasDelUsuario() {
        Reserva r1 = new Reserva();
        r1.setCodigoReserva("RES001");
        r1.setEstado(Reserva.EstadoReserva.confirmada);

        when(reservaRepository.findByUsuarioIdUsuario(1))
                .thenReturn(Arrays.asList(r1));

        List<Reserva> resultado = reservaService.buscarPorUsuario(1);

        assertEquals(1, resultado.size());
        assertEquals("RES001", resultado.get(0).getCodigoReserva());
    }

    @Test
    void buscarPorCodigo_cuandoExiste_debeRetornarReserva() {
        Reserva reserva = new Reserva();
        reserva.setCodigoReserva("RES001");

        when(reservaRepository.findByCodigoReserva("RES001"))
                .thenReturn(Optional.of(reserva));

        Optional<Reserva> resultado = reservaService.buscarPorCodigo("RES001");

        assertTrue(resultado.isPresent());
        assertEquals("RES001", resultado.get().getCodigoReserva());
    }

    @Test
    void guardar_debeRetornarReservaGuardada() {
        Reserva reserva = new Reserva();
        reserva.setCodigoReserva("RES001");

        when(reservaRepository.save(reserva)).thenReturn(reserva);

        Reserva resultado = reservaService.guardar(reserva);

        assertNotNull(resultado);
        assertEquals("RES001", resultado.getCodigoReserva());
    }

    @Test
    void eliminar_debeEjecutarDelete() {
        reservaService.eliminar(1);
        verify(reservaRepository, times(1)).deleteById(1);
    }
}
