package com.api.diversity.service;

import com.api.diversity.dto.DetalleSalidaDTO;
import com.api.diversity.model.DetalleSalida;
import java.util.List;

public interface DetalleSalidaService {
    List<DetalleSalidaDTO> findAll();
    DetalleSalida findById(Long id);
    DetalleSalidaDTO save(DetalleSalidaDTO detalleSalidaDTO);
    void deleteById(Long id);
}
