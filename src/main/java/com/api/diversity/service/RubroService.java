package com.api.diversity.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.diversity.model.Rubro;
import com.api.diversity.repository.RubroRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RubroService {

    @Autowired
    private RubroRepository rubroRepository;

    public List<Rubro> listarTodos() {
        return rubroRepository.findAll();
    }

    public Optional<Rubro> buscarPorId(Long id) {
        return rubroRepository.findById(id);
    }

    public Rubro registrar(Rubro rubro) {
        if (rubro.getEstado() == null) {
            rubro.setEstado("Activo");
        }
        return rubroRepository.save(rubro);
    }

    public Rubro editar(Long id, Rubro rubroDetails) {
        Rubro rubro = rubroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rubro no encontrado con ID: " + id));

        rubro.setNombre(rubroDetails.getNombre());
        rubro.setCode(rubroDetails.getCode());
        rubro.setDescripcion(rubroDetails.getDescripcion());
        rubro.setEstado(rubroDetails.getEstado());
        rubro.setPublicId(rubroDetails.getPublicId());
        rubro.setImagenUrl(rubroDetails.getImagenUrl());

        return rubroRepository.save(rubro);
    }

    public void eliminar(Long id) {
        rubroRepository.findById(id).ifPresent(rubro -> {
            rubro.setEstado("Inactivo");
            rubroRepository.save(rubro);
        });
    }
}