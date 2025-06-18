package com.api.diversity.controller;

import com.api.diversity.model.Salida;
import com.api.diversity.model.DetalleSalida;
import com.api.diversity.service.SalidaService;
import com.api.diversity.service.DetalleSalidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/salidas")
public class SalidaController {

    @Autowired
    private SalidaService salidaService;

    @Autowired
    private DetalleSalidaService detalleSalidaService;

    // --- Métodos para Salida ---

    @GetMapping("/listar")
    public List<Salida> listarSalidas() {
        return salidaService.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Salida buscarSalida(@PathVariable Long id) {
        Optional<Salida> salida = salidaService.findById(id);
        return salida.orElse(null);
    }

    @PostMapping("/crear")
    public Salida crearSalida(@RequestBody Salida salida) {
        return salidaService.save(salida);
    }

    @PutMapping("/actualizar/{id}")
    public Salida actualizarSalida(@PathVariable Long id, @RequestBody Salida salida) {
        salida.setIdSalida(id);
        return salidaService.save(salida);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarSalida(@PathVariable Long id) {
        salidaService.deleteById(id);
        return "Salida eliminada correctamente.";
    }

    // --- Métodos para DetalleSalida ---

    @GetMapping("/detalles/listar")
    public List<DetalleSalida> listarDetalles() {
        return detalleSalidaService.findAll();
    }

    @GetMapping("/detalles/buscar/{id}")
    public DetalleSalida buscarDetalle(@PathVariable Long id) {
        Optional<DetalleSalida> detalle = detalleSalidaService.findById(id);
        return detalle.orElse(null);
    }

    @PostMapping("/detalles/crear")
    public DetalleSalida crearDetalle(@RequestBody DetalleSalida detalle) {
        return detalleSalidaService.save(detalle);
    }

    @PutMapping("/detalles/actualizar/{id}")
    public DetalleSalida actualizarDetalle(@PathVariable Long id, @RequestBody DetalleSalida detalle) {
        detalle.setIdDetalleSalida(id);
        return detalleSalidaService.save(detalle);
    }

    @DeleteMapping("/detalles/eliminar/{id}")
    public String eliminarDetalle(@PathVariable Long id) {
        detalleSalidaService.deleteById(id);
        return "Detalle de salida eliminado correctamente.";
    }
}