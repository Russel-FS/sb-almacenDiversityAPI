package com.api.diversity.controller;

import com.api.diversity.model.Inventario;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    // Simulando una base de datos en memoria
    private Map<Integer, Inventario> inventarios = new HashMap<>();
    private int nextInventarioId = 1;

    // 1. Registrar un nuevo Inventario
    @PostMapping("/registrar")
    public String registrarInventario(@RequestBody Inventario inventario) {
        inventario.setIdInventario(nextInventarioId++);
        inventarios.put(inventario.getIdInventario(), inventario);
        return "Inventario registrado: " + inventario;
    }

    // 2. Obtener la lista de todos los Inventarios
    @GetMapping("/listar")
    public Map<Integer, Inventario> listarInventarios() {
        return inventarios;
    }

    // 3. Buscar un Inventario por ID
    @GetMapping("/buscar/{id}")
    public String buscarInventario(@PathVariable Integer id) {
        Inventario inventario = inventarios.get(id);
        return inventario != null ? "Inventario encontrado: " + inventario : "Inventario no encontrado.";
    }

    // 4. Actualizar un Inventario por ID
    @PutMapping("/actualizar/{id}")
    public String actualizarInventario(@PathVariable Integer id, @RequestBody Inventario inventario) {
        Inventario existingInventario = inventarios.get(id);
        if (existingInventario != null) {
            inventario.setIdInventario(id);
            inventarios.put(id, inventario);
            return "Inventario con ID " + id + " actualizado: " + inventario;
        }
        return "Inventario no encontrado para actualizar.";
    }

    // 5. Eliminar un Inventario por ID
    @DeleteMapping("/eliminar/{id}")
    public String eliminarInventario(@PathVariable Integer id) {
        if (inventarios.containsKey(id)) {
            inventarios.remove(id);
            return "Inventario con ID " + id + " eliminado.";
        }
        return "Inventario no encontrado para eliminar.";
    }
}
