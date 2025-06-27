package com.api.diversity.controller;

import com.api.diversity.dto.SalidaDTO;
import com.api.diversity.dto.DetalleSalidaDTO;
import com.api.diversity.service.SalidaService;
import com.api.diversity.service.DetalleSalidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salidas")
public class SalidaController {

    @Autowired
    private SalidaService salidaService;

    @Autowired
    private DetalleSalidaService detalleSalidaService;

    // --- Métodos para Salida ---

    @GetMapping("/listar")
    public ResponseEntity<List<SalidaDTO>> listarSalidas() {
        return ResponseEntity.ok(salidaService.findAll());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<SalidaDTO> buscarSalida(@PathVariable Long id) {
        SalidaDTO salida = salidaService.findAll().stream()
            .filter(s -> s.getIdSalida().equals(id))
            .findFirst()
            .orElse(null);
        if (salida == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(salida);
    }

    @PostMapping("/crear")
    public ResponseEntity<SalidaDTO> crearSalida(@RequestBody SalidaDTO salidaDTO) {
        SalidaDTO created = salidaService.save(salidaDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<SalidaDTO> actualizarSalida(@PathVariable Long id, @RequestBody SalidaDTO salidaDTO) {
        salidaDTO.setIdSalida(id);
        SalidaDTO updated = salidaService.save(salidaDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarSalida(@PathVariable Long id) {
        salidaService.deleteById(id);
        return ResponseEntity.ok("Salida eliminada correctamente.");
    }

    @GetMapping("/count")
    public ResponseEntity<Long> contarSalidas() {
        return ResponseEntity.ok((long) salidaService.findAll().size());
    }

    // --- Métodos para DetalleSalida ---

    @GetMapping("/detalles/listar")
    public ResponseEntity<List<DetalleSalidaDTO>> listarDetalles() {
        return ResponseEntity.ok(detalleSalidaService.findAll());
    }

    @GetMapping("/detalles/buscar/{id}")
    public ResponseEntity<DetalleSalidaDTO> buscarDetalle(@PathVariable Long id) {
        DetalleSalidaDTO detalle = detalleSalidaService.findAll().stream()
            .filter(d -> d.getIdDetalleSalida().equals(id))
            .findFirst()
            .orElse(null);
        if (detalle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detalle);
    }

    @PostMapping("/detalles/crear")
    public ResponseEntity<DetalleSalidaDTO> crearDetalle(@RequestBody DetalleSalidaDTO detalleDTO) {
        DetalleSalidaDTO created = detalleSalidaService.save(detalleDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/detalles/actualizar/{id}")
    public ResponseEntity<DetalleSalidaDTO> actualizarDetalle(@PathVariable Long id, @RequestBody DetalleSalidaDTO detalleDTO) {
        detalleDTO.setIdDetalleSalida(id);
        DetalleSalidaDTO updated = detalleSalidaService.save(detalleDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/detalles/eliminar/{id}")
    public ResponseEntity<String> eliminarDetalle(@PathVariable Long id) {
        detalleSalidaService.deleteById(id);
        return ResponseEntity.ok("Detalle de salida eliminado correctamente.");
    }

   
    // Manejo global de errores simples
    @ExceptionHandler({ IllegalArgumentException.class, RuntimeException.class })
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}