package com.api.diversity.mappers;

import org.springframework.stereotype.Component;
import com.api.diversity.dtos.DetalleEntradaDto;
import com.api.diversity.model.DetalleEntrada;

@Component
public class DetalleEntradaMapper {

    public DetalleEntradaDto toDto(DetalleEntrada detalle) {
        DetalleEntradaDto dto = new DetalleEntradaDto();
        dto.setIdDetalleEntrada(detalle.getIdDetalleEntrada());
        dto.setIdEntrada(detalle.getEntrada().getIdEntrada());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());
        dto.setFechaRegistro(detalle.getFechaRegistro());
        dto.setEstado(detalle.getEstado() != null ? detalle.getEstado().name() : null);

        if (detalle.getProducto() != null) {
            dto.setIdProducto(detalle.getProducto().getIdProducto());
            dto.setCodigoProducto(detalle.getProducto().getCodigoProducto());
            dto.setNombreProducto(detalle.getProducto().getNombreProducto());
        }

        if (detalle.getUsuarioRegistro() != null) {
            dto.setIdUsuarioRegistro(detalle.getUsuarioRegistro().getIdUsuario());
            dto.setNombreUsuarioRegistro(detalle.getUsuarioRegistro().getNombreCompleto());
        }

        return dto;
    }
}