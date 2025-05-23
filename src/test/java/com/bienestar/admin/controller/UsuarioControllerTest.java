/*package com.bienestar.admin.controller;

import com.bienestar.admin.model.Usuario;
import com.bienestar.admin.service.UsuarioService;
import com.bienestar.admin.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;



import java.util.Collections;
import java.util.Map;
import java.util.List;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false) // Desactiva seguridad durante pruebas
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private JwtUtil jwtUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void login_usuarioValido_retornaToken() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setRol("ADMIN");

        when(usuarioService.autenticar("admin@correo.com", "1234")).thenReturn(usuario);
        when(jwtUtil.generarToken(1L, "ADMIN")).thenReturn("fake-jwt-token");

        String json = objectMapper.writeValueAsString(
            Map.of("correo", "admin@correo.com", "contraseña", "1234")
        );

        mockMvc.perform(post("/api/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"));
    }


    @Test
    void obtenerUsuarioPorId_usuarioExiste_retornaPerfil() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setRol("ESTUDIANTE");
        usuario.setCorreo("test@correo.com");

        when(usuarioService.obtenerPorId(1L)).thenReturn(usuario);

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.correo").value("test@correo.com"));
    }

    @Test
    void obtenerTodos_debeRetornarListaDeUsuarios() throws Exception {
        Usuario u = new Usuario();
        u.setId(1L); u.setCorreo("ejemplo@eci.com");

        when(usuarioService.obtenerTodos()).thenReturn(List.of(u));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void obtenerPorRol_rolValido_debeRetornarUsuarios() throws Exception {
        Usuario u = new Usuario();
        u.setRol("Estudiante");

        when(usuarioService.obtenerPorRol("Estudiante")).thenReturn(List.of(u));

        mockMvc.perform(get("/api/usuarios/rol/Estudiante"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rol").value("Estudiante"));
    }

    @Test
    void obtenerPorCorreo_correoValido_debeRetornarUsuario() throws Exception {
        Usuario u = new Usuario();
        u.setCorreo("test@eci.edu.co");

        when(usuarioService.obtenerPorCorreo("test@eci.edu.co")).thenReturn(u);

        mockMvc.perform(get("/api/usuarios/correo/test@eci.edu.co"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("test@eci.edu.co"));
    }

    @Test
    void buscarPorNombre_nombreValido_debeRetornarUsuarios() throws Exception {
        Usuario u = new Usuario();
        u.setNombreCompleto("Carlos Ruiz");

        when(usuarioService.buscarPorNombre("Carlos")).thenReturn(List.of(u));

        mockMvc.perform(get("/api/usuarios/buscar?nombre=Carlos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreCompleto").value("Carlos Ruiz"));
    }

    @Test
    void obtenerPorId_usuarioNoExiste_retornaError404() throws Exception {
        when(usuarioService.obtenerPorId(999L)).thenThrow(new RuntimeException("Usuario no encontrado"));

        mockMvc.perform(get("/api/usuarios/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerPorCorreo_usuarioNoExiste_retornaError() throws Exception {
        when(usuarioService.obtenerPorCorreo("no@existe.com")).thenThrow(new RuntimeException("Usuario no encontrado"));

        mockMvc.perform(get("/api/usuarios/correo/no@existe.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorNombre_sinCoincidencias_retornaListaVacia() throws Exception {
        when(usuarioService.buscarPorNombre("XYZ"))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/usuarios/buscar?nombre=XYZ"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void registrarUsuario_valido_devuelveUsuario() throws Exception {
        Usuario nuevo = new Usuario();
        nuevo.setId(1L);
        nuevo.setCorreo("nuevo@eci.com");

        when(usuarioService.registrar(any(Usuario.class))).thenReturn(nuevo);

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("nuevo@eci.com"));
    }

    @Test
    void actualizarUsuario_existente_devuelveActualizado() throws Exception {
        Usuario datos = new Usuario();
        datos.setCorreo("actualizado@eci.com");

        Usuario actualizado = new Usuario();
        actualizado.setId(1L);
        actualizado.setCorreo("actualizado@eci.com");

        when(usuarioService.actualizar(eq(1L), any(Usuario.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(datos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("actualizado@eci.com"));
    }

    @Test
    void eliminarUsuario_existente_noRetornaContenido() throws Exception {
        doNothing().when(usuarioService).eliminar(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void login_credencialesInvalidas_retornaError() throws Exception {
        when(usuarioService.autenticar("invalido@eci.com", "wrong"))
                .thenThrow(new RuntimeException("Credenciales inválidas"));

        String json = objectMapper.writeValueAsString(
                Map.of("correo", "invalido@eci.com", "contraseña", "wrong")
        );

        mockMvc.perform(post("/api/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void actualizarUsuario_inexistente_lanzaError() throws Exception {
        Usuario datos = new Usuario();
        datos.setCorreo("inexistente@eci.com");

        when(usuarioService.actualizar(eq(99L), any(Usuario.class)))
                .thenThrow(new RuntimeException("Usuario no encontrado"));

        mockMvc.perform(put("/api/usuarios/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(datos)))
                .andExpect(status().isNotFound());
    }

    
}
*/