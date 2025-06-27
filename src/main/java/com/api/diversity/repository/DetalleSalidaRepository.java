package com.api.diversity.repository;

import com.api.diversity.model.DetalleSalida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleSalidaRepository extends JpaRepository<DetalleSalida, Long> {
    DetalleSalida findBySalida_IdSalidaAndProducto_IdProducto(Long idSalida, Long idProducto);
}
