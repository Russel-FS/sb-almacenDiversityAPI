package com.api.diversity.controller;

import com.api.diversity.dto.ProveedoresDTO;

import com.api.diversity.service.ProveedoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProovedoresController {

    @Autowired
    private ProveedoresService proveedoresService;

    @GetMapping("/listar")
    public List<ProveedoresDTO> listarProveedores() {
        return proveedoresService.findAll();
    }

    @PostMapping("/crear")
    public ProveedoresDTO crearProveedor(@RequestBody ProveedoresDTO proveedorDTO) {
        return proveedoresService.save(proveedorDTO);
    }

 

@GetMapping("/buscar/{id}")
public ProveedoresDTO obtenerProveedorPorId(@PathVariable Long id) {
    return com.api.diversity.mapper.ProveedoresMapper.toDTO(proveedoresService.findById(id));
}

    @PutMapping("/actualizar/{id}")
    public ProveedoresDTO actualizarProveedor(@PathVariable Long id, @RequestBody ProveedoresDTO proveedorDTO) {
        proveedorDTO.setIdProveedor(id);
        return proveedoresService.save(proveedorDTO);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable Long id) {
        proveedoresService.deleteById(id);
        return "Proveedor eliminado correctamente.";
    }
}


