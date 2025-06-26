package com.api.diversity.repository;

import com.api.diversity.model.Rubro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RubroRepository extends JpaRepository<Rubro, Long> {
    boolean existsByCode(String code);
}