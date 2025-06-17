package com.api.diversity.controller;

import com.api.diversity.model.Rol;
import com.api.diversity.service.RolService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping("/listar")
    public List<Rol> listarRoles() {
        return rolService.listarTodos();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarRol(@PathVariable Long id) {
        Optional<Rol> rolOptional = rolService.buscarPorId(id);
        if (rolOptional.isPresent()) {
            return ResponseEntity.ok(rolOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado con ID: " + id);
    }

    @PostMapping("/registrar")
    public ResponseEntity<Rol> registrarRol(@RequestBody Rol rol) {
        Rol nuevoRol = rolService.registrar(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRol);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarRol(@PathVariable Long id, @RequestBody Rol rolDetails) {
        try {
            Rol rolActualizado = rolService.editar(id, rolDetails);
            return ResponseEntity.ok(rolActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarRol(@PathVariable Long id) {
        try {
            rolService.eliminar(id);
            return ResponseEntity.ok().body("Rol con ID " + id + " eliminado correctamente.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}