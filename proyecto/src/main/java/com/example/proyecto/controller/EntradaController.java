package com.example.proyecto.controller;

import com.example.proyecto.model.Entrada;
import com.example.proyecto.service.EntradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entradas")
@CrossOrigin(origins = "*")
public class EntradaController {

    @Autowired
    private EntradaService service;

    // Listar todas las entradas
    @GetMapping
    public List<Entrada> getAll() {
        return service.listarTodas();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Entrada> getById(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva entrada
    @PostMapping
    public Entrada create(@RequestBody Entrada entrada) {
        return service.registrar(entrada);
    }

    // Actualizar una entrada
    @PutMapping("/{id}")
    public ResponseEntity<Entrada> update(@PathVariable Long id, @RequestBody Entrada entrada) {
        Entrada actualizada = service.actualizar(id, entrada);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una entrada (con respuesta tipo texto)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Se eliminó el id: " + id);
    }
}