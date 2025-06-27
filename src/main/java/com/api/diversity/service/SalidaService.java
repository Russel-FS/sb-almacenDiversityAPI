package com.api.diversity.service;

import com.api.diversity.dto.SalidaDTO;
import com.api.diversity.model.Salida;
import java.util.List;

public interface SalidaService {
    List<SalidaDTO> findAll();
    Salida findById(Long id);
    SalidaDTO save(SalidaDTO salidaDTO);
    void deleteById(Long id);
}
