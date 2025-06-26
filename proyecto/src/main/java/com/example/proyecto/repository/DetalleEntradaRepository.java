package com.example.proyecto.repository;

import com.example.proyecto.model.DetalleEntrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; 

@Repository
public interface DetalleEntradaRepository extends JpaRepository<DetalleEntrada, Long> {
    List<DetalleEntrada> findByEntradaIdEntrada(Long idEntrada);
}