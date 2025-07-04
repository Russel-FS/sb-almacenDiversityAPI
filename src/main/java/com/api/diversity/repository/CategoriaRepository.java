package com.api.diversity.repository;

import com.api.diversity.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByRubroIdRubro(Long idRubro);

    List<Categoria> findByEstado(String estado);

    Optional<Categoria> findByNombreCategoriaAndRubroIdRubro(String nombreCategoria, Long idRubro);

    boolean existsByNombreCategoriaAndRubroIdRubro(String nombreCategoria, Long idRubro);

    Optional<Categoria> findByNombreCategoria(String nombreCategoria);
}