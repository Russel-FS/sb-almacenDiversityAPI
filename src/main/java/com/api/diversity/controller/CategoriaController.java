package com.api.diversity.controller;

import com.api.diversity.model.Categoria;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @GetMapping("/listar")
    public String listarCategorias() {
        return "Lista de cateeeeeeee";
    }

    @GetMapping("/buscar/{id}")
    public String buscarCategoria(@PathVariable Integer id) {
        return "" + id;
    }

    @PostMapping("/crear")
    public String crearCategoria(@RequestBody Categoria categoria) {
        return "";
    }

    @PutMapping("/actualizar/{id}")
    public String actualizarCategoria(@PathVariable Integer id, @RequestBody Categoria categoria) {
        return " " + id;
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Integer id) {
        return "" + id;
    }
}