package com.bienestar.admin.util;

import com.auth0.jwt.algorithms.Algorithm;
import com.bienestar.admin.model.Usuario;
import com.bienestar.admin.service.UsuarioService;
import io.jsonwebtoken.*;
import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Date;

@Component
public class JwtUtil {
    @Autowired
    private UsuarioService userService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String generarToken(String idUsuario, String rol) {
        Usuario user = userService. obtenerPorId(idUsuario);
        if (user == null) {
            throw new IllegalArgumentException("No se encontró el usuario con ID: " + idUsuario);
        }
        // Definir el tiempo de expiración, por ejemplo, 1 hora desde la emisión
        Date expirationTime = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
        String token=JWT.create()
                .withClaim("id", user.getId())
                .withClaim("role", user.getRol())
                .withExpiresAt(expirationTime)
                .sign(Algorithm.HMAC256("secret"));
        return token;
    }
}