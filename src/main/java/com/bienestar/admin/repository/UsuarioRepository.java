package com.bienestar.admin.repository;

import com.bienestar.admin.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByCorreoAndContraseña(String correo, String contraseña);

    Optional<Usuario> findByCorreo(String correo);

    List<Usuario> findByRol(String rol);

    List<Usuario> findByNombreCompletoContainingIgnoreCase(String nombre);
}
