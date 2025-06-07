package com.api.diversity.controller;

import com.api.diversity.model.Usuario;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Usuario")
public class UsuarioController {

    private static List<Usuario> usuarios = new ArrayList<>();

    // GET: /api/Usuario/listar
    @GetMapping("/listar")
    public List<Usuario> listarUsuarios() {
        return usuarios;
    }

    // GET: /api/Usuario/buscar/{id} 
    @GetMapping("/buscar/{id}")
    public Usuario buscarUsuario(@PathVariable Integer id) {
        return usuarios.stream()
                .filter(usuario -> usuario.getIdUsuario().equals(id))
                .findFirst()
                .orElse(null);
    }

    // POST: /api/Usuario/crear 
    @PostMapping("/crear")
    public Map<String, Object> crearUsuario(@RequestBody Usuario usuario) {
        usuario.setIdUsuario(usuarios.size() + 1);
        usuarios.add(usuario);

       
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Usuario creado exitosamente");
        response.put("usuario", usuario); 

        return response;
    }

    // PUT: /api/Usuario/actualizar/{id} 
    @PutMapping("/actualizar/{id}")
    public Map<String, Object> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        Usuario usuarioActual = buscarUsuario(id);
        Map<String, Object> response = new HashMap<>();

        if (usuarioActual != null) {
            usuarioActual.setNombreUsuario(usuario.getNombreUsuario());
            usuarioActual.setRol(usuario.getRol());
            usuarioActual.setContraseña(usuario.getContraseña());
            
            response.put("mensaje", "Usuario con ID " + id + " actualizado correctamente.");
            response.put("usuario", usuarioActual);
        } else {
            response.put("mensaje", "Error: No se encontró el usuario con ID " + id);
            response.put("usuario", null);
        }
        return response;
    }

    // DELETE: /api/Usuario/eliminar/{id} 
    @DeleteMapping("/eliminar/{id}")
    public Map<String, Object> eliminarUsuario(@PathVariable Integer id) {
        Usuario usuarioAEliminar = buscarUsuario(id);
        Map<String, Object> response = new HashMap<>();

        if (usuarioAEliminar != null) {
            usuarios.remove(usuarioAEliminar);
            response.put("mensaje", "Usuario con ID " + id + " ha sido eliminado.");
            response.put("usuarioEliminado", usuarioAEliminar);
        } else {
            response.put("mensaje", "Error: No se pudo eliminar. No se encontró el usuario con ID " + id);
        }
        return response;
    }
}