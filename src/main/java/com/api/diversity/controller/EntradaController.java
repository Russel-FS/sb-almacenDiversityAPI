package com.api.diversity.controller;

import com.api.diversity.dtos.EntradaDto;
import com.api.diversity.model.Entrada;
import com.api.diversity.service.EntradaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/entradas")
public class EntradaController {

    @Autowired
    private EntradaService entradaService;

    @GetMapping("/listar")
    public List<EntradaDto> listarTodasLasEntradas() {
        return entradaService.listarTodos();
    }

    @GetMapping("/listar-por-proveedor/{idProveedor}")
    public List<EntradaDto> listarEntradasPorProveedor(@PathVariable Long idProveedor) {
        return entradaService.listarPorProveedor(idProveedor);
    }

    @GetMapping("/listar-por-estado/{estado}")
    public List<EntradaDto> listarEntradasPorEstado(@PathVariable String estado) {
        return entradaService.listarPorEstado(estado);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarEntrada(@PathVariable Long id) {
        Optional<EntradaDto> entradaOptional = entradaService.buscarPorId(id);
        if (entradaOptional.isPresent()) {
            return ResponseEntity.ok(entradaOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entrada no encontrada con ID: " + id);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarEntrada(@RequestBody Entrada entrada) {
        try {
            EntradaDto nuevaEntrada = entradaService.registrar(entrada);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEntrada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar entrada: " + e.getMessage());
        }
    }

    @PutMapping("/aprobar/{id}")
    public ResponseEntity<?> aprobarEntrada(@PathVariable Long id, @RequestParam Long idUsuarioAprobacion) {
        try {
            EntradaDto entradaAprobada = entradaService.aprobar(id, idUsuarioAprobacion);
            return ResponseEntity.ok(entradaAprobada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al aprobar entrada: " + e.getMessage());
        }
    }

    @PutMapping("/anular/{id}")
    public ResponseEntity<?> anularEntrada(@PathVariable Long id) {
        try {
            EntradaDto entradaAnulada = entradaService.anular(id);
            return ResponseEntity.ok(entradaAnulada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al anular entrada: " + e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarEntrada(@PathVariable Long id, @RequestBody Entrada entradaDetails) {
        try {
            EntradaDto entradaActualizada = entradaService.editar(id, entradaDetails);
            return ResponseEntity.ok(entradaActualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al editar entrada: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEntrada(@PathVariable Long id) {
        try {
            entradaService.eliminar(id);
            return ResponseEntity.ok().body("Entrada con ID " + id + " eliminada correctamente.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
} 