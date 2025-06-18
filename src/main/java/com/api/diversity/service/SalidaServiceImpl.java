package com.api.diversity.service;

import com.api.diversity.model.Salida;
import com.api.diversity.repository.SalidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalidaServiceImpl implements SalidaService {

    @Autowired
    private SalidaRepository salidaRepository;

    @Override
    public List<Salida> findAll() {
        return salidaRepository.findAll();
    }

    @Override
    public Optional<Salida> findById(Long id) {
        return salidaRepository.findById(id);
    }

    @Override
    public Salida save(Salida salida) {
        return salidaRepository.save(salida);
    }

    @Override
    public void deleteById(Long id) {
        salidaRepository.deleteById(id);
    }
}
