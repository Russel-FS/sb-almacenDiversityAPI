package com.api.diversity.controller;

import com.api.diversity.model.Proveedores;
import com.api.diversity.service.ProveedoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proveedores")
public class ProovedoresController {
    
    @Autowired
    private ProveedoresService proveedoresService;

    @GetMapping("/listar")
    public List<Proveedores> listarProveedores() {
        return proveedoresService.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Proveedores buscarProveedor(@PathVariable Long id) {
        Optional<Proveedores> proveedor = proveedoresService.findById(id);
        return proveedor.orElse(null);
    }

    @PostMapping("/crear")
    public Proveedores crearProveedor(@RequestBody Proveedores proveedor) {
        return proveedoresService.save(proveedor);
    }

    @PutMapping("/actualizar/{id}")
    public Proveedores actualizarProveedor(@PathVariable Long id, @RequestBody Proveedores proveedor) {
        proveedor.setIdProveedor(id);
        return proveedoresService.save(proveedor);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable Long id) {
        proveedoresService.deleteById(id);
        return "Proveedor eliminado correctamente.";
    }
}


