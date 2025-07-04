package com.api.diversity.mappers;

import org.springframework.stereotype.Component;
import com.api.diversity.dtos.EntradaDto;
import com.api.diversity.model.Entrada;

@Component
public class EntradaMapper {

    public EntradaDto toDto(Entrada entrada) {
        EntradaDto dto = new EntradaDto();
        dto.setIdEntrada(entrada.getIdEntrada());
        dto.setNumeroFactura(entrada.getNumeroFactura());
        dto.setFechaEntrada(entrada.getFechaEntrada());
        dto.setCostoTotal(entrada.getCostoTotal());
        dto.setEstado(entrada.getEstado() != null ? entrada.getEstado().name() : null);
        dto.setFechaAprobacion(entrada.getFechaAprobacion());
        dto.setObservaciones(entrada.getObservaciones());

        if (entrada.getProveedor() != null) {
            dto.setIdProveedor(entrada.getProveedor().getIdProveedor());
            dto.setRazonSocialProveedor(entrada.getProveedor().getRazonSocial());
        }

        if (entrada.getUsuarioRegistro() != null) {
            dto.setIdUsuarioRegistro(entrada.getUsuarioRegistro().getIdUsuario());
            dto.setNombreUsuarioRegistro(entrada.getUsuarioRegistro().getNombreCompleto());
        }

        if (entrada.getUsuarioAprobacion() != null) {
            dto.setIdUsuarioAprobacion(entrada.getUsuarioAprobacion().getIdUsuario());
            dto.setNombreUsuarioAprobacion(entrada.getUsuarioAprobacion().getNombreCompleto());
        }

        return dto;
    }
}