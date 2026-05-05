package com.example.flytrack;

import com.example.flytrack.entities.Vuelo;
import com.example.flytrack.repositories.VueloRepository;
import com.example.flytrack.services.VueloService;
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

public class VueloServiceTest {

    @Mock
    private VueloRepository vueloRepository;

    @InjectMocks
    private VueloService vueloService;

    @Test
    void listarTodos_debeRetornarListaDeVuelos() {
        Vuelo v1 = new Vuelo();
        v1.setIdVuelo(1);
        v1.setNumeroVuelo("AV123");
        v1.setEstado(Vuelo.EstadoVuelo.programado);

        Vuelo v2 = new Vuelo();
        v2.setIdVuelo(2);
        v2.setNumeroVuelo("LA456");
        v2.setEstado(Vuelo.EstadoVuelo.en_vuelo);

        when(vueloRepository.findAll()).thenReturn(Arrays.asList(v1, v2));

        List<Vuelo> resultado = vueloService.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("AV123", resultado.get(0).getNumeroVuelo());
        verify(vueloRepository, times(1)).findAll();
    }

    @Test
    void buscarPorEstado_debeRetornarVuelosFiltrados() {
        Vuelo v1 = new Vuelo();
        v1.setNumeroVuelo("AV123");
        v1.setEstado(Vuelo.EstadoVuelo.programado);

        when(vueloRepository.findByEstado(Vuelo.EstadoVuelo.programado))
                .thenReturn(Arrays.asList(v1));

        List<Vuelo> resultado = vueloService.buscarPorEstado(Vuelo.EstadoVuelo.programado);

        assertEquals(1, resultado.size());
        assertEquals(Vuelo.EstadoVuelo.programado, resultado.get(0).getEstado());
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarVuelo() {
        Vuelo vuelo = new Vuelo();
        vuelo.setIdVuelo(1);
        vuelo.setNumeroVuelo("AV123");

        when(vueloRepository.findById(1)).thenReturn(Optional.of(vuelo));

        Optional<Vuelo> resultado = vueloService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("AV123", resultado.get().getNumeroVuelo());
    }

    @Test
    void guardar_debeRetornarVueloGuardado() {
        Vuelo vuelo = new Vuelo();
        vuelo.setNumeroVuelo("AV123");

        when(vueloRepository.save(vuelo)).thenReturn(vuelo);

        Vuelo resultado = vueloService.guardar(vuelo);

        assertNotNull(resultado);
        assertEquals("AV123", resultado.getNumeroVuelo());
    }
}
