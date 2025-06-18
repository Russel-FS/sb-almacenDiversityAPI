package com.api.diversity.repository;

import com.api.diversity.model.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedoresRepository extends JpaRepository<Proveedores, Long> {
    // Métodos personalizados si los necesitas
}
