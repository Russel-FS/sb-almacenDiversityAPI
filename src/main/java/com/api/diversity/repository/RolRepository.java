package com.api.diversity.repository;

import com.api.diversity.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    boolean existsByNombreRol(String nombre);

    Optional<Rol> findByNombreRol(String nombreRol);
}