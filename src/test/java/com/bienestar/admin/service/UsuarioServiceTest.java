/*package com.bienestar.admin.service;

import com.bienestar.admin.model.Usuario;
import com.bienestar.admin.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioService = new UsuarioService();
        // Inyección manual del campo privado
        ReflectionTestUtils.setField(usuarioService, "usuarioRepository", usuarioRepository);
    }

    @Test
    void autenticar_usuarioExistente_retornaUsuario() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("test@correo.com");
        usuario.setContraseña("1234");

        when(usuarioRepository.findByCorreoAndContraseña("test@correo.com", "1234"))
                .thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.autenticar("test@correo.com", "1234");

        assertNotNull(resultado);
        assertEquals("test@correo.com", resultado.getCorreo());
    }

    @Test
    void obtenerPorId_usuarioInexistente_lanzaExcepcion() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            usuarioService.obtenerPorId(99L);
        });

        assertEquals("Usuario no encontrado", ex.getMessage());
    }

    @Test
    void obtenerTodos_retornaListaUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(new Usuario(), new Usuario()));

        List<Usuario> usuarios = usuarioService.obtenerTodos();

        assertEquals(2, usuarios.size());
    }

    @Test
    void obtenerPorRol_retornaUsuariosConEseRol() {
        when(usuarioRepository.findByRol("ADMIN"))
                .thenReturn(List.of(new Usuario()));

        List<Usuario> usuarios = usuarioService.obtenerPorRol("ADMIN");

        assertEquals(1, usuarios.size());
    }

    @Test
    void buscarPorNombre_coincidencia_retornaLista() {
        when(usuarioRepository.findByNombreCompletoContainingIgnoreCase("Carlos"))
                .thenReturn(List.of(new Usuario()));

        List<Usuario> resultado = usuarioService.buscarPorNombre("Carlos");

        assertFalse(resultado.isEmpty());
    }

    @Test
    void registrar_usuarioNuevo_devuelveGuardado() {
        Usuario nuevo = new Usuario();
        nuevo.setCorreo("nuevo@eci.com");

        when(usuarioRepository.save(nuevo)).thenReturn(nuevo);

        Usuario guardado = usuarioService.registrar(nuevo);

        assertEquals("nuevo@eci.com", guardado.getCorreo());
    }

    @Test
    void eliminar_usuarioPorId_noLanzaError() {
        assertDoesNotThrow(() -> usuarioService.eliminar(10L));
        verify(usuarioRepository, times(1)).deleteById(10L);
    }

    @Test
    void actualizar_usuarioExistente_devuelveActualizado() {
        Usuario original = new Usuario();
        original.setId(5L);
        original.setCorreo("original@eci.com");

        Usuario cambios = new Usuario();
        cambios.setCorreo("nuevo@eci.com");

        when(usuarioRepository.findById(5L)).thenReturn(Optional.of(original));
        when(usuarioRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Usuario actualizado = usuarioService.actualizar(5L, cambios);

        assertEquals("nuevo@eci.com", actualizado.getCorreo());
    }
}

*/