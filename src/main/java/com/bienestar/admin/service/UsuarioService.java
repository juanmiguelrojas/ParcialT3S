package com.bienestar.admin.service;

import com.bienestar.admin.model.Usuario;
import com.bienestar.admin.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario autenticar(String correo, String contraseña) {
        return usuarioRepository.findByCorreoAndContraseña(correo, contraseña)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
    }

    public Usuario obtenerPorId(String id) {
    return usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
}


    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> obtenerPorRol(String rol) {
        return usuarioRepository.findByRol(rol);
    }

    public Usuario obtenerPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ese correo"));
    }

    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombreCompletoContainingIgnoreCase(nombre);
    }

    public Usuario registrar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(String id, Usuario datos) {
        Usuario usuario = obtenerPorId(id);
        usuario.setCorreo(datos.getCorreo());
        usuario.setTelefono(datos.getTelefono());
        usuario.setNombreCompleto(datos.getNombreCompleto());
        usuario.setCedula(datos.getCedula());
        usuario.setCelular(datos.getCelular());
        usuario.setContactoEmergencia(datos.getContactoEmergencia());
        usuario.setGenero(datos.getGenero());
        usuario.setFechaNacimiento(datos.getFechaNacimiento());
        usuario.setEps(datos.getEps());
        usuario.setRh(datos.getRh());
        usuario.setEstadoCivil(datos.getEstadoCivil());
        usuario.setTipoUsuario(datos.getTipoUsuario());
        usuario.setRol(datos.getRol());

        return usuarioRepository.save(usuario);
    }

    public void eliminar(String id) {
        usuarioRepository.deleteById(id);
    }
}
