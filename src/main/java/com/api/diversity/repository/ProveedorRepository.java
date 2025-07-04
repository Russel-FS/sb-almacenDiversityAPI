package com.api.diversity.repository;

import com.api.diversity.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    Optional<Proveedor> findByRuc(String ruc);

    Optional<Proveedor> findByEmail(String email);

    boolean existsByRuc(String ruc);

    boolean existsByEmail(String email);
}