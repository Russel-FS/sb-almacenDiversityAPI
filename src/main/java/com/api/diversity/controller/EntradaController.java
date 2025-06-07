package com.api.diversity.controller;

import com.api.diversity.model.Entrada;
import com.api.diversity.model.DetalleEntrada;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/entradas")
public class EntradaController {

    // Simulando una base de datos en memoria
    private Map<Integer, Entrada> entradas = new HashMap<>();
    private Map<Integer, DetalleEntrada> detallesEntrada = new HashMap<>();
    private int nextEntradaId = 1;
    private int nextDetalleEntradaId = 1;

    // 1. Registrar una nueva Entrada
    @PostMapping("/registrar")
    public String crearEntrada(@RequestBody Entrada entrada) {
        entrada.setIdEntrada(nextEntradaId++);
        entradas.put(entrada.getIdEntrada(), entrada);
        return "Entrada registrada: " + entrada;
    }

    // 2. Obtener la lista de todas las Entradas
    @GetMapping("/listar")
    public Map<Integer, Entrada> listarEntradas() {
        return entradas;
    }

    // 3. Buscar una Entrada por su ID
    @GetMapping("/buscar/{id}")
    public String buscarEntrada(@PathVariable Integer id) {
        Entrada entrada = entradas.get(id);
        return entrada != null ? "Entrada encontrada: " + entrada : "Entrada no encontrada.";
    }

    // 4. Actualizar una Entrada por su ID
    @PutMapping("/actualizar/{id}")
    public String actualizarEntrada(@PathVariable Integer id, @RequestBody Entrada entrada) {
        Entrada existingEntrada = entradas.get(id);
        if (existingEntrada != null) {
            entrada.setIdEntrada(id);
            entradas.put(id, entrada);
            return "Entrada con ID " + id + " actualizada: " + entrada;
        }
        return "Entrada no encontrada para actualizar.";
    }

    // 5. Eliminar una Entrada por su ID
    @DeleteMapping("/eliminar/{id}")
    public String eliminarEntrada(@PathVariable Integer id) {
        if (entradas.containsKey(id)) {
            entradas.remove(id);
            return "Entrada con ID " + id + " eliminada.";
        }
        return "Entrada no encontrada para eliminar.";
    }

    // 6. Registrar un Detalle de Entrada
    @PostMapping("/detalledeentrada/registrar")
    public String registrarDetalleEntrada(@RequestBody DetalleEntrada detalleEntrada) {
        detalleEntrada.setIdDetalleEntrada(nextDetalleEntradaId++);
        detallesEntrada.put(detalleEntrada.getIdDetalleEntrada(), detalleEntrada);
        return "Detalle de entrada registrado: " + detalleEntrada;
    }

    // 7. Obtener la lista de todos los Detalles de Entrada
    @GetMapping("/detalledeentrada/listar")
    public Map<Integer, DetalleEntrada> listarDetallesEntrada() {
        return detallesEntrada;
    }

    // 8. Buscar un Detalle de Entrada por su ID
    @GetMapping("/detalledeentrada/buscar/{id}")
    public String buscarDetalleEntrada(@PathVariable Integer id) {
        DetalleEntrada detalle = detallesEntrada.get(id);
        return detalle != null ? "Detalle de entrada encontrado: " + detalle : "Detalle de entrada no encontrado.";
    }

    // 9. Actualizar un Detalle de Entrada por su ID
    @PutMapping("/detalledeentrada/actualizar/{id}")
    public String actualizarDetalleEntrada(@PathVariable Integer id, @RequestBody DetalleEntrada detalleEntrada) {
        DetalleEntrada existingDetalle = detallesEntrada.get(id);
        if (existingDetalle != null) {
            detalleEntrada.setIdDetalleEntrada(id);
            detallesEntrada.put(id, detalleEntrada);
            return "Detalle de entrada con ID " + id + " actualizado: " + detalleEntrada;
        }
        return "Detalle de entrada no encontrado para actualizar.";
    }

    // 10. Eliminar un Detalle de Entrada por su ID
    @DeleteMapping("/detalledeentrada/eliminar/{id}")
    public String eliminarDetalleEntrada(@PathVariable Integer id) {
        if (detallesEntrada.containsKey(id)) {
            detallesEntrada.remove(id);
            return "Detalle de entrada con ID " + id + " eliminado.";
        }
        return "Detalle de entrada no encontrado para eliminar.";
    }
}