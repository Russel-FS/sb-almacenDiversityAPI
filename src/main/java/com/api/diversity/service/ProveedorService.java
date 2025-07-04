package com.api.diversity.service;

import com.api.diversity.model.Proveedor;
import com.api.diversity.repository.ProveedorRepository;
import com.api.diversity.dtos.ProveedorDto;
import com.api.diversity.mappers.ProveedorMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    private final ProveedorMapper mapper;

    public List<ProveedorDto> listarTodos() {
        List<Proveedor> proveedores = proveedorRepository.findAll();
        return proveedores.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ProveedorDto> buscarPorId(Long id) {
        return proveedorRepository.findById(id)
                .map(mapper::toDto);
    }

    public ProveedorDto registrar(Proveedor proveedor) {
        // Validar que no exista un proveedor con el mismo RUC
        if (proveedorRepository.existsByRuc(proveedor.getRuc())) {
            throw new EntityExistsException("Ya existe un proveedor con este RUC");
        }

        // Validar que no exista un proveedor con el mismo email (si se proporciona)
        if (proveedor.getEmail() != null && !proveedor.getEmail().trim().isEmpty() &&
                proveedorRepository.existsByEmail(proveedor.getEmail())) {
            throw new EntityExistsException("Ya existe un proveedor con este email");
        }

        if (proveedor.getEstado() == null) {
            proveedor.setEstado("Activo");
        }

        return mapper.toDto(proveedorRepository.save(proveedor));
    }

    public ProveedorDto editar(Long id, Proveedor proveedorDetails) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado con ID: " + id));

        if (proveedorDetails.getRazonSocial() != null) {
            proveedor.setRazonSocial(proveedorDetails.getRazonSocial());
        }
        if (proveedorDetails.getDireccion() != null) {
            proveedor.setDireccion(proveedorDetails.getDireccion());
        }
        if (proveedorDetails.getTelefono() != null) {
            proveedor.setTelefono(proveedorDetails.getTelefono());
        }
        if (proveedorDetails.getEmail() != null) {
            proveedor.setEmail(proveedorDetails.getEmail());
        }
        if (proveedorDetails.getEstado() != null) {
            proveedor.setEstado(proveedorDetails.getEstado());
        }

        return mapper.toDto(proveedorRepository.save(proveedor));
    }

    public void eliminar(Long id) {
        proveedorRepository.findById(id).ifPresent(proveedor -> {
            proveedor.setEstado("Inactivo");
            proveedorRepository.save(proveedor);
        });
    }
}