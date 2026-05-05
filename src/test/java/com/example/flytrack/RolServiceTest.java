package com.example.flytrack;

import com.example.flytrack.entities.Rol;
import com.example.flytrack.repositories.RolRepository;
import com.example.flytrack.services.RolService;
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

public class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    @Test
    void listarTodos_debeRetornarListaDeRoles() {
        Rol r1 = new Rol();
        r1.setIdRol(1);
        r1.setDescripcion("admin");

        Rol r2 = new Rol();
        r2.setIdRol(2);
        r2.setDescripcion("pasajero");

        when(rolRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Rol> resultado = rolService.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("admin", resultado.get(0).getDescripcion());
        verify(rolRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarRol() {
        Rol rol = new Rol();
        rol.setIdRol(1);
        rol.setDescripcion("admin");

        when(rolRepository.findById(1)).thenReturn(Optional.of(rol));

        Optional<Rol> resultado = rolService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("admin", resultado.get().getDescripcion());
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeRetornarVacio() {
        when(rolRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Rol> resultado = rolService.buscarPorId(99);

        assertFalse(resultado.isPresent());
    }

    @Test
    void guardar_debeRetornarRolGuardado() {
        Rol rol = new Rol();
        rol.setDescripcion("pasajero");

        when(rolRepository.save(rol)).thenReturn(rol);

        Rol resultado = rolService.guardar(rol);

        assertNotNull(resultado);
        assertEquals("pasajero", resultado.getDescripcion());
        verify(rolRepository, times(1)).save(rol);
    }

    @Test
    void eliminar_debeEjecutarDelete() {
        rolService.eliminar(1);
        verify(rolRepository, times(1)).deleteById(1);
    }
}
