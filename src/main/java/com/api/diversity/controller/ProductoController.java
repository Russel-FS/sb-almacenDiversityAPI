package com.api.diversity.controller;

import com.api.diversity.model.Producto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @GetMapping("/listar")
    public String listarProductos() {
        return "Lista de productos";
    }

    @GetMapping("/buscar/{id}")
    public String buscarProducto(@PathVariable String id) {
        return "" + id;
    }

    @PostMapping("/crear")
    public String crearProducto(@RequestBody Producto producto) {
        return "";
    }

    @PutMapping("/actualizar/{id}")
    public String actualizarProducto(@PathVariable String id, @RequestBody Producto producto) {
        return "" + id;
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable String id) {
        return "" + id;
    }
}