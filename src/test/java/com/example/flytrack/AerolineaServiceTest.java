package com.example.flytrack;

import com.example.flytrack.entities.Aerolinea;
import com.example.flytrack.repositories.AerolineaRepository;
import com.example.flytrack.services.AerolineaService;
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
public class AerolineaServiceTest {

    @Mock
    private AerolineaRepository aerolineaRepository;

    @InjectMocks
    private AerolineaService aerolineaService;

    @Test
    void listarTodos_debeRetornarListaDeAerolineas() {
        Aerolinea a1 = new Aerolinea();
        a1.setIdAerolinea(1);
        a1.setNombre("Avianca");
        a1.setCodigoIata("AV");
        a1.setPais("Colombia");

        Aerolinea a2 = new Aerolinea();
        a2.setIdAerolinea(2);
        a2.setNombre("LATAM");
        a2.setCodigoIata("LA");
        a2.setPais("Chile");

        when(aerolineaRepository.findAll()).thenReturn(Arrays.asList(a1, a2));

        List<Aerolinea> resultado = aerolineaService.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Avianca", resultado.get(0).getNombre());
        verify(aerolineaRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarAerolinea() {
        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setIdAerolinea(1);
        aerolinea.setNombre("Avianca");

        when(aerolineaRepository.findById(1)).thenReturn(Optional.of(aerolinea));

        Optional<Aerolinea> resultado = aerolineaService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Avianca", resultado.get().getNombre());
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeRetornarVacio() {
        when(aerolineaRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Aerolinea> resultado = aerolineaService.buscarPorId(99);

        assertFalse(resultado.isPresent());
    }

    @Test
    void guardar_debeRetornarAerolineaGuardada() {
        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setNombre("Avianca");

        when(aerolineaRepository.save(aerolinea)).thenReturn(aerolinea);

        Aerolinea resultado = aerolineaService.guardar(aerolinea);

        assertNotNull(resultado);
        assertEquals("Avianca", resultado.getNombre());
    }

    @Test
    void eliminar_debeEjecutarDelete() {
        aerolineaService.eliminar(1);
        verify(aerolineaRepository, times(1)).deleteById(1);
    }
}
