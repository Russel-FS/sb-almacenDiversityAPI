package com.api.diversity.mappers;

import org.springframework.stereotype.Component;
import com.api.diversity.dtos.SalidaDto;
import com.api.diversity.model.Salida;

@Component
public class SalidaMapper {

    public SalidaDto toDto(Salida salida) {
        SalidaDto dto = new SalidaDto();
        dto.setIdSalida(salida.getIdSalida());
        dto.setNumeroDocumento(salida.getNumeroDocumento());
        dto.setTipoDocumento(salida.getTipoDocumento() != null ? salida.getTipoDocumento().name() : null);
        dto.setFechaSalida(salida.getFechaSalida());
        dto.setMotivoSalida(salida.getMotivoSalida());
        dto.setTotalVenta(salida.getTotalVenta());
        dto.setEstado(salida.getEstado() != null ? salida.getEstado().name() : null);
        dto.setFechaAprobacion(salida.getFechaAprobacion());
        dto.setObservaciones(salida.getObservaciones());

        if (salida.getCliente() != null) {
            dto.setIdCliente(salida.getCliente().getIdCliente());
            dto.setNombreCliente(salida.getCliente().getNombreCliente());
        }

        if (salida.getUsuarioRegistro() != null) {
            dto.setIdUsuarioRegistro(salida.getUsuarioRegistro().getIdUsuario());
            dto.setNombreUsuarioRegistro(salida.getUsuarioRegistro().getNombreCompleto());
        }

        if (salida.getUsuarioAprobacion() != null) {
            dto.setIdUsuarioAprobacion(salida.getUsuarioAprobacion().getIdUsuario());
            dto.setNombreUsuarioAprobacion(salida.getUsuarioAprobacion().getNombreCompleto());
        }

        return dto;
    }
}