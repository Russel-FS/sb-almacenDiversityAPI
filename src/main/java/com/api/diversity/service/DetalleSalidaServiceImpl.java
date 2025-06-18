package com.api.diversity.service;

import com.api.diversity.model.DetalleSalida;
import com.api.diversity.repository.DetalleSalidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleSalidaServiceImpl implements DetalleSalidaService {

    @Autowired
    private DetalleSalidaRepository detalleSalidaRepository;

    @Override
    public List<DetalleSalida> findAll() {
        return detalleSalidaRepository.findAll();
    }

    @Override
    public Optional<DetalleSalida> findById(Long id) {
        return detalleSalidaRepository.findById(id);
    }

    @Override
    public DetalleSalida save(DetalleSalida detalleSalida) {
        return detalleSalidaRepository.save(detalleSalida);
    }

    @Override
    public void deleteById(Long id) {
        detalleSalidaRepository.deleteById(id);
    }
}
