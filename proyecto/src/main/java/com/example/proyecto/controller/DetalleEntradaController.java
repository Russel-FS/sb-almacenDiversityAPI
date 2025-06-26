package com.example.proyecto.controller;

import com.example.proyecto.model.DetalleEntrada;
import com.example.proyecto.service.DetalleEntradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-entradas")
@CrossOrigin(origins = "*")
public class DetalleEntradaController {

    @Autowired
    private DetalleEntradaService service;

    // Listar todos los detalles
    @GetMapping
    public List<DetalleEntrada> getAll() {
        return service.listarTodos();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<DetalleEntrada> getById(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo detalle
    @PostMapping
    public DetalleEntrada create(@RequestBody DetalleEntrada detalleEntrada) {
        return service.registrar(detalleEntrada);
    }

    // Actualizar un detalle
    @PutMapping("/{id}")
    public ResponseEntity<DetalleEntrada> update(@PathVariable Long id, @RequestBody DetalleEntrada detalleEntrada) {
        DetalleEntrada actualizado = service.actualizar(id, detalleEntrada);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un detalle (respuesta tipo texto)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Se eliminó el detalle con id: " + id);
    }

   @GetMapping("/entrada/{idEntrada}")
public ResponseEntity<?> obtenerPorIdEntrada(@PathVariable Long idEntrada) {
    List<DetalleEntrada> detalles = service.buscarPorIdEntrada(idEntrada);
    if (detalles.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontraron detalles para la entrada con ID " + idEntrada);
    }
    return ResponseEntity.ok(detalles);
}
}
