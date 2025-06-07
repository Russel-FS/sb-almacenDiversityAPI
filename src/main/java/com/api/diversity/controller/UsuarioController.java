package com.api.diversity.controller;

import com.api.diversity.model.Usuario;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @GetMapping("/listar")
    public String listarUsuarios() {
        return "Lista de usuarios";
    }

    @GetMapping("/buscar/{id}")
    public String buscarUsuario(@PathVariable Integer id) {
        return "" + id;
    }

    @PostMapping("/crear")
    public String crearUsuario(@RequestBody Usuario usuario) {
        return "";
    }

    @PutMapping("/actualizar/{id}")
    public String actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        return "" + id;
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id) {
        return "" + id;
    }
}