package com.api.diversity.mapper;

import com.api.diversity.dto.SalidaDTO;
import com.api.diversity.model.Salida;

public class SalidaMapper {
    public static SalidaDTO toDTO(Salida entity) {
        if (entity == null) return null;
        SalidaDTO dto = new SalidaDTO();
        dto.setIdSalida(entity.getIdSalida());
        dto.setNumeroDocumento(entity.getNumeroDocumento());
        dto.setTipoDocumento(entity.getTipoDocumento());
        dto.setFechaSalida(entity.getFechaSalida());
        dto.setMotivoSalida(entity.getMotivoSalida());
        dto.setTotalVenta(entity.getTotalVenta());
        dto.setEstado(entity.getEstado());
        dto.setIdUsuarioRegistro(entity.getUsuarioRegistro() != null && entity.getUsuarioRegistro().getIdUsuario() != null
            ? entity.getUsuarioRegistro().getIdUsuario().longValue()
            : null);
        dto.setIdUsuarioAprobacion(entity.getUsuarioAprobacion() != null && entity.getUsuarioAprobacion().getIdUsuario() != null
            ? entity.getUsuarioAprobacion().getIdUsuario().longValue()
            : null);
        dto.setFechaAprobacion(entity.getFechaAprobacion());
        dto.setObservaciones(entity.getObservaciones());
        return dto;
    }

    public static Salida toEntity(SalidaDTO dto) {
        if (dto == null) return null;
        Salida entity = new Salida();
        entity.setNumeroDocumento(dto.getNumeroDocumento());
        entity.setTipoDocumento(dto.getTipoDocumento());
        entity.setMotivoSalida(dto.getMotivoSalida());
        entity.setTotalVenta(dto.getTotalVenta());
        entity.setEstado(dto.getEstado());
        entity.setFechaAprobacion(dto.getFechaAprobacion());
        entity.setObservaciones(dto.getObservaciones());
        return entity;
    }
}
