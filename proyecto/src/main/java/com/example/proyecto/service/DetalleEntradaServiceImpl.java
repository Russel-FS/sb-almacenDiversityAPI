package com.example.proyecto.service;

import com.example.proyecto.model.DetalleEntrada;
import com.example.proyecto.model.Entrada;
import com.example.proyecto.repository.DetalleEntradaRepository;
import com.example.proyecto.repository.EntradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleEntradaServiceImpl implements DetalleEntradaService {

    @Autowired
    private DetalleEntradaRepository repository;

    @Autowired
    private EntradaRepository entradaRepository;

    @Override
    public List<DetalleEntrada> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<DetalleEntrada> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public DetalleEntrada registrar(DetalleEntrada detalleEntrada) {
        if (detalleEntrada.getEntrada() != null && detalleEntrada.getEntrada().getIdEntrada() != null) {
            Entrada entrada = entradaRepository.findById(detalleEntrada.getEntrada().getIdEntrada()).orElse(null);
            detalleEntrada.setEntrada(entrada);
        }
        return repository.save(detalleEntrada);
    }

    @Override
    public DetalleEntrada actualizar(Long id, DetalleEntrada actualizado) {
        return repository.findById(id).map(detalle -> {
            if (actualizado.getEntrada() != null && actualizado.getEntrada().getIdEntrada() != null) {
                Entrada entrada = entradaRepository.findById(actualizado.getEntrada().getIdEntrada()).orElse(null);
                detalle.setEntrada(entrada);
            }
            detalle.setIdProducto(actualizado.getIdProducto());
            detalle.setCantidad(actualizado.getCantidad());
            detalle.setPrecioUnitario(actualizado.getPrecioUnitario());
            detalle.setSubtotal(actualizado.getSubtotal());
            detalle.setIdUsuarioRegistro(actualizado.getIdUsuarioRegistro());
            detalle.setFechaRegistro(actualizado.getFechaRegistro());
            detalle.setEstado(actualizado.getEstado());
            return repository.save(detalle);
        }).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    
    @Override
    public List<DetalleEntrada> buscarPorIdEntrada(Long idEntrada) {
        return repository.findByEntradaIdEntrada(idEntrada);
    }
}