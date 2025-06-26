package com.example.proyecto.repository;

import com.example.proyecto.model.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    // Aquí puedes agregar consultas personalizadas si las necesitas
}