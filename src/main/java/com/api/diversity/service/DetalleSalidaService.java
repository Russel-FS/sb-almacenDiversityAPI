package com.api.diversity.service;

import com.api.diversity.model.DetalleSalida;
import java.util.List;
import java.util.Optional;

public interface DetalleSalidaService {
    List<DetalleSalida> findAll();
    Optional<DetalleSalida> findById(Long id);
    DetalleSalida save(DetalleSalida detalleSalida);
    void deleteById(Long id);
}
