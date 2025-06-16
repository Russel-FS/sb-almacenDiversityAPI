package com.api.diversity.service;

import com.api.diversity.model.Rol;
import com.api.diversity.repository.RolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
        if (!rolRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar. Rol no encontrado con ID: " + id);
        }
        rolRepository.deleteById(id);
    }
}