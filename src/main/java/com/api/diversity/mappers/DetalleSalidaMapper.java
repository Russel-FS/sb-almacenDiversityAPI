package com.api.diversity.mappers;

import org.springframework.stereotype.Component;
import com.api.diversity.dtos.DetalleSalidaDto;
import com.api.diversity.model.DetalleSalida;

@Component
public class DetalleSalidaMapper {

    public DetalleSalidaDto toDto(DetalleSalida detalle) {
        DetalleSalidaDto dto = new DetalleSalidaDto();
        dto.setIdDetalleSalida(detalle.getIdDetalleSalida());
        dto.setIdSalida(detalle.getSalida().getIdSalida());
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