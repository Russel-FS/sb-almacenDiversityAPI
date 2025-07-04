package com.api.diversity.repository;

import com.api.diversity.model.DetalleEntrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetalleEntradaRepository extends JpaRepository<DetalleEntrada, Long> {
    List<DetalleEntrada> findByEntradaIdEntrada(Long idEntrada);

    List<DetalleEntrada> findByProductoIdProducto(Long idProducto);

    List<DetalleEntrada> findByEstado(DetalleEntrada.EstadoDetalle estado);
}