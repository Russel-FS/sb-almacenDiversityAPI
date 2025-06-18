package com.api.diversity.service;

import com.api.diversity.model.Proveedores;
import com.api.diversity.repository.ProveedoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedoresServiceImpl implements ProveedoresService {

    @Autowired
    private ProveedoresRepository proveedoresRepository;

    @Override
    public List<Proveedores> findAll() {
        return proveedoresRepository.findAll();
    }

    @Override
    public Optional<Proveedores> findById(Long id) {
        return proveedoresRepository.findById(id);
    }

    @Override
    public Proveedores save(Proveedores proveedor) {
        return proveedoresRepository.save(proveedor);
    }

    @Override
    public void deleteById(Long id) {
        proveedoresRepository.deleteById(id);
    }
}
