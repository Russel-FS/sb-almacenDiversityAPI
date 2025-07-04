package com.api.diversity.controller;

import com.api.diversity.dtos.SalidaDto;
import com.api.diversity.model.Salida;
import com.api.diversity.service.SalidaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/salidas")
public class SalidaController {

    @Autowired
    private SalidaService salidaService;

    @GetMapping("/listar")
    public List<SalidaDto> listarTodasLasSalidas() {
        return salidaService.listarTodos();
    }

    @GetMapping("/listar-por-cliente/{idCliente}")
    public List<SalidaDto> listarSalidasPorCliente(@PathVariable Long idCliente) {
        return salidaService.listarPorCliente(idCliente);
    }

    @GetMapping("/listar-por-estado/{estado}")
    public List<SalidaDto> listarSalidasPorEstado(@PathVariable String estado) {
        return salidaService.listarPorEstado(estado);
    }

    @GetMapping("/listar-por-tipo-documento/{tipoDocumento}")
    public List<SalidaDto> listarSalidasPorTipoDocumento(@PathVariable String tipoDocumento) {
        return salidaService.listarPorTipoDocumento(tipoDocumento);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarSalida(@PathVariable Long id) {
        Optional<SalidaDto> salidaOptional = salidaService.buscarPorId(id);
        if (salidaOptional.isPresent()) {
            return ResponseEntity.ok(salidaOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Salida no encontrada con ID: " + id);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarSalida(@RequestBody Salida salida) {
        try {
            SalidaDto nuevaSalida = salidaService.registrar(salida);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSalida);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar salida: " + e.getMessage());
        }
    }

    @PutMapping("/aprobar/{id}")
    public ResponseEntity<?> aprobarSalida(@PathVariable Long id, @RequestParam Long idUsuarioAprobacion) {
        try {
            SalidaDto salidaAprobada = salidaService.aprobar(id, idUsuarioAprobacion);
            return ResponseEntity.ok(salidaAprobada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al aprobar salida: " + e.getMessage());
        }
    }

    @PutMapping("/anular/{id}")
    public ResponseEntity<?> anularSalida(@PathVariable Long id) {
        try {
            SalidaDto salidaAnulada = salidaService.anular(id);
            return ResponseEntity.ok(salidaAnulada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al anular salida: " + e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarSalida(@PathVariable Long id, @RequestBody Salida salidaDetails) {
        try {
            SalidaDto salidaActualizada = salidaService.editar(id, salidaDetails);
            return ResponseEntity.ok(salidaActualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al editar salida: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarSalida(@PathVariable Long id) {
        try {
            salidaService.eliminar(id);
            return ResponseEntity.ok().body("Salida con ID " + id + " eliminada correctamente.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}