package com.api.diversity.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.diversity.model.Rol;
import com.api.diversity.repository.RolRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }

    public Optional<Rol> buscarPorId(Long id) {
        return rolRepository.findById(id);
    }

    public Rol registrar(Rol rol) {

        if (rolRepository.existsByNombreRol(rol.getNombreRol())) {
            throw new EntityExistsException("nombre del rol ya existe");
        }
        if (rol.getEstado() == null) {
            rol.setEstado("Activo");
        }
        return rolRepository.save(rol);
    }

    public Rol editar(Long id, Rol rolDetails) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con ID: " + id));

        rol.setNombreRol(rolDetails.getNombreRol());
        rol.setDescripcion(rolDetails.getDescripcion());
        rol.setEstado(rolDetails.getEstado());

        return rolRepository.save(rol);
    }

    public void eliminar(Long id) {
        rolRepository.findById(id).ifPresent(rol -> {
            rol.setEstado("Inactivo");
            rolRepository.save(rol);
        });
    }
}