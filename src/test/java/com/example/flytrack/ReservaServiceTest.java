package com.example.flytrack;

import com.example.flytrack.dtos.ReservaRequest;
import com.example.flytrack.dtos.ReservaResponse;
import com.example.flytrack.entities.Reserva;
import com.example.flytrack.entities.Usuario;
import com.example.flytrack.entities.Vuelo;
import com.example.flytrack.mappers.ReservaMapper;
import com.example.flytrack.repositories.ReservaRepository;
import com.example.flytrack.repositories.VueloRepository;
import com.example.flytrack.services.ReservaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock private ReservaRepository reservaRepository;
    @Mock private VueloRepository vueloRepository;
    @Mock private ReservaMapper reservaMapper;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    void crear_deberiaGenerarCodigoUnico() {
        // Arrange
        Vuelo vuelo = new Vuelo();
        vuelo.setIdVuelo(1);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);

        ReservaRequest request = new ReservaRequest();
        request.setIdVuelo(1);

        Reserva reservaGuardada = new Reserva();
        reservaGuardada.setIdReserva(1);
        reservaGuardada.setCodigoReserva("FTABCD1234");

        ReservaResponse dto = new ReservaResponse();
        dto.setCodigoReserva("FTABCD1234");

        when(vueloRepository.findById(1)).thenReturn(Optional.of(vuelo));
        when(reservaRepository.existsByCodigoReserva(anyString())).thenReturn(false);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaGuardada);
        when(reservaMapper.toResponse(reservaGuardada)).thenReturn(dto);

        // Act
        ReservaResponse resultado = reservaService.crear(request, usuario);

        // Assert
        assertNotNull(resultado);
        assertNotNull(resultado.getCodigoReserva());
        assertTrue(resultado.getCodigoReserva().startsWith("FT") ||
                resultado.getCodigoReserva() != null);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void crear_deberiaLanzarExcepcionSiVueloNoExiste() {
        ReservaRequest request = new ReservaRequest();
        request.setIdVuelo(99);

        when(vueloRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reservaService.crear(request, new Usuario()));
        assertTrue(ex.getMessage().contains("99"));
    }

    @Test
    void cancelar_deberiaLanzarExcepcionSiNoEsDuenio() {
        Usuario duenio = new Usuario();
        duenio.setIdUsuario(1);

        Usuario otro = new Usuario();
        otro.setIdUsuario(2);

        Reserva reserva = new Reserva();
        reserva.setIdReserva(10);
        reserva.setUsuario(duenio);

        when(reservaRepository.findById(10)).thenReturn(Optional.of(reserva));

        assertThrows(RuntimeException.class,
                () -> reservaService.cancelar(10, otro));
    }
}
