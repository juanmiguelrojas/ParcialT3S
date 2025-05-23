package com.bienestar.admin.controller;

import com.bienestar.admin.model.Usuario;
import com.bienestar.admin.service.UsuarioService;
import com.bienestar.admin.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> datos) {
        Usuario usuario =usuarioService.autenticar(datos.get("correo"), datos.get("contraseña"));
        String token = jwtUtil.generarToken(usuario.getId(), usuario.getRol());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/perfil")
    public ResponseEntity<?> perfil(HttpServletRequest request) {
        String idUsuario = request.getAttribute("idUsuario").toString();
        Usuario usuario = usuarioService.obtenerPorId(idUsuario);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("id", usuario.getId());
        respuesta.put("rol", usuario.getRol());
        respuesta.put("correo", usuario.getCorreo());
        respuesta.put("telefono", usuario.getTelefono());
        respuesta.put("nombreCompleto", usuario.getNombreCompleto());
        respuesta.put("cedula", usuario.getCedula());
        respuesta.put("celular", usuario.getCelular());
        respuesta.put("contactoEmergencia", usuario.getContactoEmergencia());
        respuesta.put("genero", usuario.getGenero());
        respuesta.put("fechaNacimiento", usuario.getFechaNacimiento());
        respuesta.put("eps", usuario.getEps());
        respuesta.put("rh", usuario.getRh());
        respuesta.put("estadoCivil", usuario.getEstadoCivil());
        respuesta.put("tipoUsuario", usuario.getTipoUsuario());

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/porid/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable String id) {
        Usuario usuario = usuarioService.obtenerPorId(id);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("id", usuario.getId());
        respuesta.put("rol", usuario.getRol());
        respuesta.put("correo", usuario.getCorreo());
        respuesta.put("telefono", usuario.getTelefono());
        respuesta.put("nombreCompleto", usuario.getNombreCompleto());
        respuesta.put("cedula", usuario.getCedula());
        respuesta.put("celular", usuario.getCelular());
        respuesta.put("contactoEmergencia", usuario.getContactoEmergencia());
        respuesta.put("genero", usuario.getGenero());
        respuesta.put("fechaNacimiento", usuario.getFechaNacimiento());
        respuesta.put("eps", usuario.getEps());
        respuesta.put("rh", usuario.getRh());
        respuesta.put("estadoCivil", usuario.getEstadoCivil());
        respuesta.put("tipoUsuario", usuario.getTipoUsuario());

        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable String id, @RequestBody Usuario datos) {
        Usuario actualizado = usuarioService.actualizar(id, datos);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
