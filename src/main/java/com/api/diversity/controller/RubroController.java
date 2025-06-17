package com.api.diversity.controller;

import com.api.diversity.model.Rubro;
import com.api.diversity.service.RubroService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rubros")
public class RubroController {

    @Autowired
    private RubroService rubroService;

    @GetMapping("/listar")
    public List<Rubro> listarRubros() {
        return rubroService.listarTodos();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarRubro(@PathVariable Long id) {
        Optional<Rubro> rubroOptional = rubroService.buscarPorId(id);
        if (rubroOptional.isPresent()) {
            return ResponseEntity.ok(rubroOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rubro no encontrado con ID: " + id);
    }

    @PostMapping("/registrar")
    public ResponseEntity<Rubro> registrarRubro(@RequestBody Rubro rubro) {
        Rubro nuevoRubro = rubroService.registrar(rubro);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRubro);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarRubro(@PathVariable Long id, @RequestBody Rubro rubroDetails) {
        try {
            Rubro rubroActualizado = rubroService.editar(id, rubroDetails);
            return ResponseEntity.ok(rubroActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarRubro(@PathVariable Long id) {
        try {
            rubroService.eliminar(id);
            return ResponseEntity.ok().body("Rubro con ID " + id + " eliminado correctamente.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}