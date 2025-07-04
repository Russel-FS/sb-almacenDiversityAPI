package com.api.diversity.repository;

import com.api.diversity.model.DetalleSalida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetalleSalidaRepository extends JpaRepository<DetalleSalida, Long> {
    List<DetalleSalida> findBySalidaIdSalida(Long idSalida);

    List<DetalleSalida> findByProductoIdProducto(Long idProducto);

    List<DetalleSalida> findByEstado(DetalleSalida.EstadoDetalle estado);
}