package com.api.diversity.service;

import com.api.diversity.dto.ProveedoresDTO;
import com.api.diversity.model.Proveedores;
import java.util.List;


public interface ProveedoresService {
    List<ProveedoresDTO> findAll();
    Proveedores findById(Long id);
    ProveedoresDTO save(ProveedoresDTO proveedorDTO);
    void deleteById(Long id);
}
