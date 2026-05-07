package com.example.flytrack;

import com.example.flytrack.dtos.VueloRequest;
import com.example.flytrack.dtos.VueloResponse;
import com.example.flytrack.entities.Aerolinea;
import com.example.flytrack.entities.Aeropuerto;
import com.example.flytrack.entities.Vuelo;
import com.example.flytrack.enums.EstadoVuelo;
import com.example.flytrack.mappers.VueloMapper;
import com.example.flytrack.repositories.AerolineaRepository;
import com.example.flytrack.repositories.AeropuertoRepository;
import com.example.flytrack.repositories.VueloRepository;
import com.example.flytrack.services.NotificacionService;
import com.example.flytrack.services.VueloService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VueloServiceTest {

    @Mock private VueloRepository vueloRepository;
    @Mock private AerolineaRepository aerolineaRepository;
    @Mock private AeropuertoRepository aeropuertoRepository;
    @Mock private VueloMapper vueloMapper;
    @Mock private NotificacionService notificacionService;

    @InjectMocks
    private VueloService vueloService;

    private Aerolinea aerolinea;
    private Aeropuerto origen;
    private Aeropuerto destino;
    private Vuelo vuelo;

    @BeforeEach
    void setUp() {
        aerolinea = new Aerolinea();
        aerolinea.setIdAerolinea(1);
        aerolinea.setNombre("Avianca");
        aerolinea.setCodigoIata("AV");

        origen = new Aeropuerto();
        origen.setIdAeropuerto(1);
        origen.setCodigoIata("BOG");

        destino = new Aeropuerto();
        destino.setIdAeropuerto(2);
        destino.setCodigoIata("MDE");

        vuelo = new Vuelo();
        vuelo.setIdVuelo(1);
        vuelo.setNumeroVuelo("AV123");
        vuelo.setAerolinea(aerolinea);
        vuelo.setOrigen(origen);
        vuelo.setDestino(destino);
        vuelo.setFechaSalida(LocalDateTime.now().plusDays(1));
        vuelo.setFechaLlegada(LocalDateTime.now().plusDays(1).plusHours(1));
        vuelo.setEstado(EstadoVuelo.programado);
    }

    @Test
    void buscarPorId_deberiaRetornarVuelo() {
        VueloResponse dto = new VueloResponse();
        dto.setIdVuelo(1);
        dto.setNumeroVuelo("AV123");

        when(vueloRepository.findById(1)).thenReturn(Optional.of(vuelo));
        when(vueloMapper.toResponse(vuelo)).thenReturn(dto);

        VueloResponse resultado = vueloService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals("AV123", resultado.getNumeroVuelo());
        verify(vueloRepository, times(1)).findById(1);
    }

    @Test
    void buscarPorId_deberiLanzarExcepcionSiNoExiste() {
        when(vueloRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> vueloService.buscarPorId(99));
        assertTrue(ex.getMessage().contains("99"));
    }

    @Test
    void actualizar_cuandoCambiaEstado_deberiaNotificarPasajeros() {
        VueloRequest request = new VueloRequest();
        request.setEstado(EstadoVuelo.cancelado);

        VueloResponse dto = new VueloResponse();
        dto.setEstado(EstadoVuelo.cancelado);

        when(vueloRepository.findById(1)).thenReturn(Optional.of(vuelo));
        when(vueloRepository.save(any())).thenReturn(vuelo);
        when(vueloMapper.toResponse(vuelo)).thenReturn(dto);

        vueloService.actualizar(1, request);

        // Verificar que se llamó al servicio de notificaciones
        verify(notificacionService, times(1)).notificarCambioEstadoVuelo(any(Vuelo.class));
    }

    @Test
    void actualizar_cuandoNoHayCambioEstado_noDeberiaNotificar() {
        VueloRequest request = new VueloRequest();
        request.setPuertaEmbarque("B5"); // Solo cambia la puerta, no el estado

        VueloResponse dto = new VueloResponse();
        when(vueloRepository.findById(1)).thenReturn(Optional.of(vuelo));
        when(vueloRepository.save(any())).thenReturn(vuelo);
        when(vueloMapper.toResponse(vuelo)).thenReturn(dto);

        vueloService.actualizar(1, request);

        // No debe notificar si el estado no cambió
        verify(notificacionService, never()).notificarCambioEstadoVuelo(any());
    }
}
