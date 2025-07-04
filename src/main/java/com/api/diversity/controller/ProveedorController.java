package com.api.diversity.controller;

import com.api.diversity.dtos.ProveedorDto;
import com.api.diversity.model.Proveedor;
import com.api.diversity.service.ProveedorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/listar")
    public List<ProveedorDto> listarTodosLosProveedores() {
        return proveedorService.listarTodos();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarProveedor(@PathVariable Long id) {
        Optional<ProveedorDto> proveedorOptional = proveedorService.buscarPorId(id);
        if (proveedorOptional.isPresent()) {
            return ResponseEntity.ok(proveedorOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proveedor no encontrado con ID: " + id);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarProveedor(@RequestBody Proveedor proveedor) {
        try {
            ProveedorDto nuevoProveedor = proveedorService.registrar(proveedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProveedor);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar proveedor: " + e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarProveedor(@PathVariable Long id, @RequestBody Proveedor proveedorDetails) {
        try {
            ProveedorDto proveedorActualizado = proveedorService.editar(id, proveedorDetails);
            return ResponseEntity.ok(proveedorActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al editar proveedor: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarProveedor(@PathVariable Long id) {
        try {
            proveedorService.eliminar(id);
            return ResponseEntity.ok().body("Proveedor con ID " + id + " eliminado correctamente.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}