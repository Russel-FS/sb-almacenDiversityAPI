package com.api.diversity.service;

import com.api.diversity.model.Proveedores;
import java.util.List;
import java.util.Optional;

public interface ProveedoresService {
    List<Proveedores> findAll();
    Optional<Proveedores> findById(Long id);
    Proveedores save(Proveedores proveedor);
    void deleteById(Long id);
}
