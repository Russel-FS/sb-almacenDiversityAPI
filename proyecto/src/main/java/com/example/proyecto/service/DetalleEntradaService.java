package com.example.proyecto.service;

import com.example.proyecto.model.DetalleEntrada;
import java.util.List;
import java.util.Optional;

public interface DetalleEntradaService {
    List<DetalleEntrada> listarTodos();
    Optional<DetalleEntrada> buscarPorId(Long id);
    DetalleEntrada registrar(DetalleEntrada detalleEntrada);
    DetalleEntrada actualizar(Long id, DetalleEntrada detalleEntrada);
    void eliminar(Long id);
    List<DetalleEntrada> buscarPorIdEntrada(Long idEntrada);

}
