package com.api.diversity.mapper;

import com.api.diversity.dto.DetalleSalidaDTO;
import com.api.diversity.model.DetalleSalida;
import com.api.diversity.model.Producto;
import com.api.diversity.model.Salida;
import com.api.diversity.model.Usuario;
import java.time.LocalDateTime;

public class DetalleSalidaMapper {
    public static DetalleSalidaDTO toDTO(DetalleSalida entity) {
        if (entity == null) return null;
        DetalleSalidaDTO dto = new DetalleSalidaDTO();
        dto.setIdDetalleSalida(entity.getIdDetalleSalida());
        dto.setIdSalida(entity.getSalida() != null ? entity.getSalida().getIdSalida() : null);
        dto.setIdProducto(entity.getProducto() != null ? entity.getProducto().getIdProducto() : null);
        dto.setCantidad(entity.getCantidad());
        dto.setPrecioUnitario(entity.getPrecioUnitario());
        dto.setSubtotal(entity.getSubtotal());
        dto.setIdUsuarioRegistro(entity.getUsuarioRegistro() != null && entity.getUsuarioRegistro().getIdUsuario() != null
            ? entity.getUsuarioRegistro().getIdUsuario().longValue()
            : null);
        dto.setFechaRegistro(entity.getFechaRegistro());
        dto.setEstado(entity.getEstado());
        return dto;
    }

    public static DetalleSalida toEntity(DetalleSalidaDTO dto) {
        if (dto == null) return null;
        if (dto.getEstado() == null || dto.getEstado().isEmpty()) {
            throw new IllegalArgumentException("El estado es obligatorio.");
        }
        DetalleSalida entity = new DetalleSalida();
        entity.setCantidad(dto.getCantidad());
        entity.setPrecioUnitario(dto.getPrecioUnitario());
        entity.setSubtotal(dto.getSubtotal());
        entity.setEstado(dto.getEstado());

        // Asignar Producto si el idProducto viene en el DTO
        if (dto.getIdProducto() != null) {
            Producto producto = new Producto();
            producto.setIdProducto(dto.getIdProducto());
            entity.setProducto(producto);
        }

        // Asignar Salida si el idSalida viene en el DTO
        if (dto.getIdSalida() != null) {
            Salida salida = new Salida();
            salida.setIdSalida(dto.getIdSalida());
            entity.setSalida(salida);
        }

        // Asignar UsuarioRegistro si el idUsuarioRegistro viene en el DTO
        if (dto.getIdUsuarioRegistro() != null) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(dto.getIdUsuarioRegistro() != null ? dto.getIdUsuarioRegistro().intValue() : null);
            entity.setUsuarioRegistro(usuario);
        }

        // Si fechaRegistro es null, asignar fecha y hora actual
        if (dto.getFechaRegistro() != null) {
            entity.setFechaRegistro(dto.getFechaRegistro());
        } else {
            entity.setFechaRegistro(LocalDateTime.now());
        }

        return entity;
    }
}

