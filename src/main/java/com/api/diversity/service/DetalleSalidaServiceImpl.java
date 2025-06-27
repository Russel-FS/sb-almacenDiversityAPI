package com.api.diversity.service;

import com.api.diversity.dto.DetalleSalidaDTO;
import com.api.diversity.mapper.DetalleSalidaMapper;
import com.api.diversity.model.DetalleSalida;
import com.api.diversity.model.Producto;
import com.api.diversity.model.Salida;
import com.api.diversity.model.Usuario;
import com.api.diversity.repository.DetalleSalidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleSalidaServiceImpl implements DetalleSalidaService {

    @Autowired
    private DetalleSalidaRepository detalleSalidaRepository;

    @Override
    public List<DetalleSalidaDTO> findAll() {
        return detalleSalidaRepository.findAll()
                .stream()
                .map(DetalleSalidaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DetalleSalida findById(Long id) {
        return detalleSalidaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("DetalleSalida no encontrado"));
    }

    @Override
    public DetalleSalidaDTO save(DetalleSalidaDTO dto) {
        DetalleSalida entity;
        if (dto.getIdDetalleSalida() != null) {
            // Actualización: buscar y actualizar campos
            entity = detalleSalidaRepository.findById(dto.getIdDetalleSalida())
                .orElseThrow(() -> new IllegalArgumentException("DetalleSalida no encontrado para actualizar."));

            // Validar que no exista otro registro con la misma combinación salida-producto
            DetalleSalida existente = detalleSalidaRepository.findBySalida_IdSalidaAndProducto_IdProducto(
                dto.getIdSalida(), dto.getIdProducto());
            if (existente != null && !existente.getIdDetalleSalida().equals(dto.getIdDetalleSalida())) {
                throw new IllegalArgumentException("Ya existe un detalle con ese producto para esta salida.");
            }

            entity.setCantidad(dto.getCantidad());
            entity.setPrecioUnitario(dto.getPrecioUnitario());
            entity.setSubtotal(dto.getSubtotal());
            entity.setEstado(dto.getEstado());

            if (dto.getIdProducto() != null) {
                Producto producto = new Producto();
                producto.setIdProducto(dto.getIdProducto());
                entity.setProducto(producto);
            }
            if (dto.getIdSalida() != null) {
                Salida salida = new Salida();
                salida.setIdSalida(dto.getIdSalida());
                entity.setSalida(salida);
            }
            if (dto.getIdUsuarioRegistro() != null) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(dto.getIdUsuarioRegistro().intValue());
                entity.setUsuarioRegistro(usuario);
            }
            if (dto.getFechaRegistro() != null) {
                entity.setFechaRegistro(dto.getFechaRegistro());
            }
        } else {
            // Creación: validar duplicados
            DetalleSalida existente = detalleSalidaRepository.findBySalida_IdSalidaAndProducto_IdProducto(
                dto.getIdSalida(), dto.getIdProducto());
            if (existente != null) {
                throw new IllegalArgumentException("Ya existe un detalle con ese producto para esta salida.");
            }
            entity = DetalleSalidaMapper.toEntity(dto);
        }
        DetalleSalida saved = detalleSalidaRepository.save(entity);
        return DetalleSalidaMapper.toDTO(saved);
    }

    @Override
    public void deleteById(Long id) {
        detalleSalidaRepository.deleteById(id);
    }
}
