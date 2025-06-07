package com.api.diversity.controller;

import com.api.diversity.model.Salida;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/salidas")
public class SalidaController {

    @GetMapping("/listar")
    public String listarSalidas() {
        return "Lista de salidas";
    }

    @GetMapping("/buscar/{id}")
    public String buscarSalida(@PathVariable Integer id) {
        return "" + id;
    }

    @PostMapping("/crear")
    public String crearSalida(@RequestBody Salida salida) {
        return "";
    }

    @PutMapping("/actualizar/{id}")
    public String actualizarSalida(@PathVariable Integer id, @RequestBody Salida salida) {
        return "" + id;
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarSalida(@PathVariable Integer id) {
        return "" + id;
    }

    @GetMapping("/detalles/{id}")
    public String listarDetallesSalida(@PathVariable Integer id) {
        return "" + id;
    }
}