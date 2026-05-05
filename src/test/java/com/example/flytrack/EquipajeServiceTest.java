package com.example.flytrack;

import com.example.flytrack.entities.Equipaje;
import com.example.flytrack.repositories.EquipajeRepository;
import com.example.flytrack.services.EquipajeService;
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
public class EquipajeServiceTest {

    @Mock
    private EquipajeRepository equipajeRepository;

    @InjectMocks
    private EquipajeService equipajeService;

    @Test
    void listarTodos_debeRetornarListaDeEquipajes() {
        Equipaje e1 = new Equipaje();
        e1.setIdEquipaje(1);
        e1.setDescripcion("Maleta grande");
        e1.setPesoKg(23.5f);
        e1.setEstado(Equipaje.EstadoEquipaje.registrado);

        Equipaje e2 = new Equipaje();
        e2.setIdEquipaje(2);
        e2.setDescripcion("Maleta pequeña");
        e2.setPesoKg(10.0f);
        e2.setEstado(Equipaje.EstadoEquipaje.en_bodega);

        when(equipajeRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<Equipaje> resultado = equipajeService.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Maleta grande", resultado.get(0).getDescripcion());
        verify(equipajeRepository, times(1)).findAll();
    }

    @Test
    void buscarPorReserva_debeRetornarEquipajesDeReserva() {
        Equipaje e1 = new Equipaje();
        e1.setDescripcion("Maleta grande");
        e1.setEstado(Equipaje.EstadoEquipaje.registrado);

        when(equipajeRepository.findByReservaIdReserva(1))
                .thenReturn(Arrays.asList(e1));

        List<Equipaje> resultado = equipajeService.buscarPorReserva(1);

        assertEquals(1, resultado.size());
        assertEquals(Equipaje.EstadoEquipaje.registrado, resultado.get(0).getEstado());
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarEquipaje() {
        Equipaje equipaje = new Equipaje();
        equipaje.setIdEquipaje(1);
        equipaje.setDescripcion("Maleta grande");

        when(equipajeRepository.findById(1)).thenReturn(Optional.of(equipaje));

        Optional<Equipaje> resultado = equipajeService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Maleta grande", resultado.get().getDescripcion());
    }

    @Test
    void guardar_debeRetornarEquipajeGuardado() {
        Equipaje equipaje = new Equipaje();
        equipaje.setDescripcion("Maleta grande");

        when(equipajeRepository.save(equipaje)).thenReturn(equipaje);

        Equipaje resultado = equipajeService.guardar(equipaje);

        assertNotNull(resultado);
        assertEquals("Maleta grande", resultado.getDescripcion());
    }

    @Test
    void eliminar_debeEjecutarDelete() {
        equipajeService.eliminar(1);
        verify(equipajeRepository, times(1)).deleteById(1);
    }
}
