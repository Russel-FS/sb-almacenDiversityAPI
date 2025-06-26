package com.example.proyecto.service;

import com.example.proyecto.model.Entrada;
import com.example.proyecto.repository.EntradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntradaServiceImpl implements EntradaService {

    @Autowired
    private EntradaRepository entradaRepository;

    @Override
    public List<Entrada> listarTodas() {
        return entradaRepository.findAll();
    }

    @Override
    public Optional<Entrada> buscarPorId(Long id) {
        return entradaRepository.findById(id);
    }

    @Override
    public Entrada registrar(Entrada entrada) {
        return entradaRepository.save(entrada);
    }

    @Override
    public Entrada actualizar(Long id, Entrada entradaActualizada) {
        return entradaRepository.findById(id).map(entrada -> {
            entrada.setNumeroFactura(entradaActualizada.getNumeroFactura());
            entrada.setIdProveedor(entradaActualizada.getIdProveedor());
            entrada.setFechaEntrada(entradaActualizada.getFechaEntrada());
            entrada.setCostoTotal(entradaActualizada.getCostoTotal());
            entrada.setEstado(entradaActualizada.getEstado());
            entrada.setIdUsuarioRegistro(entradaActualizada.getIdUsuarioRegistro());
            entrada.setIdUsuarioAprobacion(entradaActualizada.getIdUsuarioAprobacion());
            entrada.setFechaAprobacion(entradaActualizada.getFechaAprobacion());
            entrada.setObservaciones(entradaActualizada.getObservaciones());
            return entradaRepository.save(entrada);
        }).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        entradaRepository.deleteById(id);
    }
    }
