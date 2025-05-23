/*package com.bienestar.admin.util;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockFilterChain;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilAndFilterTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        jwtUtil.secret = "testsecret";
        jwtUtil.expiration = 1000 * 60 * 60; // 1 hora
    }

    @Test
    void generarToken_y_obtenerClaims_funcionaCorrectamente() {
        String token = jwtUtil.generarToken(42L, "ADMIN");

        assertNotNull(token);

        Claims claims = jwtUtil.obtenerClaims(token);

        assertEquals("42", claims.getSubject());
        assertEquals("ADMIN", claims.get("rol"));
        assertTrue(claims.getExpiration().after(new Date()));
    }

    @Test
    void jwtFilter_tokenValido_agregaAtributos() throws ServletException, IOException {
        JwtFilter jwtFilter = new JwtFilter();
        jwtFilter.jwtUtil = jwtUtil; 

        String token = jwtUtil.generarToken(99L, "ESTUDIANTE");

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);

        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain((req, res) -> {
            Long idUsuario = (Long) req.getAttribute("idUsuario");
            String rol = (String) req.getAttribute("rol");

            assertEquals(99L, idUsuario);
            assertEquals("ESTUDIANTE", rol);
        });

        jwtFilter.doFilterInternal(request, response, chain);
    }

    @Test
    void jwtFilter_tokenInvalido_noLanzaError() throws ServletException, IOException {
        JwtFilter jwtFilter = new JwtFilter();
        jwtFilter.jwtUtil = jwtUtil;

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token-falso");

        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        // No debe lanzar excepción
        assertDoesNotThrow(() -> jwtFilter.doFilterInternal(request, response, chain));
    }

    @Test
    void jwtFilter_sinToken_sigueSinFallo() throws ServletException, IOException {
        JwtFilter jwtFilter = new JwtFilter();
        jwtFilter.jwtUtil = jwtUtil;

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        // No debe lanzar excepción
        assertDoesNotThrow(() -> jwtFilter.doFilterInternal(request, response, chain));
    }
}
*/