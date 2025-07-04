package com.api.diversity.mappers;

import org.springframework.stereotype.Component;
import com.api.diversity.dtos.ProveedorDto;
import com.api.diversity.model.Proveedor;

@Component
public class ProveedorMapper {

    public ProveedorDto toDto(Proveedor proveedor) {
        ProveedorDto dto = new ProveedorDto();
        dto.setIdProveedor(proveedor.getIdProveedor());
        dto.setRazonSocial(proveedor.getRazonSocial());
        dto.setRuc(proveedor.getRuc());
        dto.setDireccion(proveedor.getDireccion());
        dto.setTelefono(proveedor.getTelefono());
        dto.setEmail(proveedor.getEmail());
        dto.setEstado(proveedor.getEstado());
        dto.setFechaCreacion(proveedor.getFechaCreacion());
        dto.setFechaModificacion(proveedor.getFechaModificacion());
        return dto;
    }
}