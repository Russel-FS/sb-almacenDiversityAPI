package com.example.proyecto.service;

import com.example.proyecto.model.Entrada;

import java.util.List;
import java.util.Optional;

public interface EntradaService {
    List<Entrada> listarTodas();
    Optional<Entrada> buscarPorId(Long id);
    Entrada registrar(Entrada entrada);
    Entrada actualizar(Long id, Entrada entrada);
    void eliminar(Long id);
}