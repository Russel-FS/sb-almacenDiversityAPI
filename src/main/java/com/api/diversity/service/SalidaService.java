package com.api.diversity.service;

import com.api.diversity.model.Salida;
import java.util.List;
import java.util.Optional;

public interface SalidaService {
    List<Salida> findAll();
    Optional<Salida> findById(Long id);
    Salida save(Salida salida);
    void deleteById(Long id);
}
