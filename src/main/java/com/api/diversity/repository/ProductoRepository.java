package com.api.diversity.repository;

import com.api.diversity.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByCodigoProducto(String codigoProducto);

    List<Producto> findByCategoriaIdCategoria(Long idCategoria);

    List<Producto> findByEstado(String estado);

    List<Producto> findByStockActualLessThanEqual(Integer stockMinimo);

    boolean existsByCodigoProducto(String codigoProducto);
}