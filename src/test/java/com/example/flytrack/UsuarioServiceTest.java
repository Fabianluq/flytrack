package com.example.flytrack;

import com.example.flytrack.entities.Usuario;
import com.example.flytrack.repositories.UsuarioRepository;
import com.example.flytrack.services.UsuarioService;
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
public class UsuarioServiceTest {

    // Mock simula el repositorio sin tocar la base de datos real
    @Mock
    private UsuarioRepository usuarioRepository;

    // InjectMocks inyecta el mock dentro del servicio
    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void listarTodos_debeRetornarListaDeUsuarios() {
        // ARRANGE: preparamos los datos de prueba
        Usuario u1 = new Usuario();
        u1.setIdUsuario(1);
        u1.setNombre("Karen");
        u1.setCorreo("karen@test.com");

        Usuario u2 = new Usuario();
        u2.setIdUsuario(2);
        u2.setNombre("Juan");
        u2.setCorreo("juan@test.com");

        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        // ACT: ejecutamos el método a probar
        List<Usuario> resultado = usuarioService.listarTodos();

        // ASSERT: verificamos que el resultado es correcto
        assertEquals(2, resultado.size());
        assertEquals("Karen", resultado.get(0).getNombre());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNombre("Karen");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Karen", resultado.get().getNombre());
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeRetornarVacio() {
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioService.buscarPorId(99);

        assertFalse(resultado.isPresent());
    }

    @Test
    void guardar_debeRetornarUsuarioGuardado() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Karen");
        usuario.setCorreo("karen@test.com");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.guardar(usuario);

        assertNotNull(resultado);
        assertEquals("Karen", resultado.getNombre());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void eliminar_debeEjecutarDelete() {
        usuarioService.eliminar(1);
        verify(usuarioRepository, times(1)).deleteById(1);
    }
}
