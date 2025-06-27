package com.api.diversity.service;

import com.api.diversity.dto.ProveedoresDTO;
import com.api.diversity.mapper.ProveedoresMapper;
import com.api.diversity.model.Proveedores;
import com.api.diversity.repository.ProveedoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProveedoresServiceImpl implements ProveedoresService {

    @Autowired
    private ProveedoresRepository proveedoresRepository;

    @Override
    public List<ProveedoresDTO> findAll() {
        return proveedoresRepository.findAll()
                .stream()
                .map(ProveedoresMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Proveedores findById(Long id) {
        return proveedoresRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("Proveedor no encontrado"));
    }


    @Override
public ProveedoresDTO save(ProveedoresDTO proveedorDTO) {
    Proveedores entity;
    if (proveedorDTO.getIdProveedor() != null) {
        entity = proveedoresRepository.findById(proveedorDTO.getIdProveedor())
            .orElse(new Proveedores());
        if (proveedorDTO.getFechaCreacion() == null) {
            proveedorDTO.setFechaCreacion(entity.getFechaCreacion());
        }
    }

    entity = ProveedoresMapper.toEntity(proveedorDTO);
    Proveedores saved = proveedoresRepository.save(entity);
    return ProveedoresMapper.toDTO(saved);
}

    @Override
    public void deleteById(Long id) {
        proveedoresRepository.deleteById(id);
    }
}
