package com.example.flytrack;

import com.example.flytrack.entities.Aeropuerto;
import com.example.flytrack.repositories.AeropuertoRepository;
import com.example.flytrack.services.AeropuertoService;
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
public class AeropuertoServiceTest {

    @Mock
    private AeropuertoRepository aeropuertoRepository;

    @InjectMocks
    private AeropuertoService aeropuertoService;

    @Test
    void listarTodos_debeRetornarListaDeAeropuertos() {
        Aeropuerto a1 = new Aeropuerto();
        a1.setIdAeropuerto(1);
        a1.setNombre("El Dorado");
        a1.setCodigoIata("BOG");
        a1.setCiudad("Bogotá");
        a1.setPais("Colombia");

        Aeropuerto a2 = new Aeropuerto();
        a2.setIdAeropuerto(2);
        a2.setNombre("Jose Maria Cordova");
        a2.setCodigoIata("MDE");
        a2.setCiudad("Medellín");
        a2.setPais("Colombia");

        when(aeropuertoRepository.findAll()).thenReturn(Arrays.asList(a1, a2));

        List<Aeropuerto> resultado = aeropuertoService.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("El Dorado", resultado.get(0).getNombre());
        verify(aeropuertoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarAeropuerto() {
        Aeropuerto aeropuerto = new Aeropuerto();
        aeropuerto.setIdAeropuerto(1);
        aeropuerto.setNombre("El Dorado");

        when(aeropuertoRepository.findById(1)).thenReturn(Optional.of(aeropuerto));

        Optional<Aeropuerto> resultado = aeropuertoService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("El Dorado", resultado.get().getNombre());
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeRetornarVacio() {
        when(aeropuertoRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Aeropuerto> resultado = aeropuertoService.buscarPorId(99);

        assertFalse(resultado.isPresent());
    }

    @Test
    void guardar_debeRetornarAeropuertoGuardado() {
        Aeropuerto aeropuerto = new Aeropuerto();
        aeropuerto.setNombre("El Dorado");

        when(aeropuertoRepository.save(aeropuerto)).thenReturn(aeropuerto);

        Aeropuerto resultado = aeropuertoService.guardar(aeropuerto);

        assertNotNull(resultado);
        assertEquals("El Dorado", resultado.getNombre());
    }

    @Test
    void eliminar_debeEjecutarDelete() {
        aeropuertoService.eliminar(1);
        verify(aeropuertoRepository, times(1)).deleteById(1);
    }
}
