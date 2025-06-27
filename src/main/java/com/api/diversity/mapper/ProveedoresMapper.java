package com.api.diversity.mapper;

import com.api.diversity.dto.ProveedoresDTO;
import com.api.diversity.model.*;

public class ProveedoresMapper {
    public static ProveedoresDTO toDTO(Proveedores entity) {
        if (entity == null) return null;
        ProveedoresDTO dto = new ProveedoresDTO();
        dto.setIdProveedor(entity.getIdProveedor());
        dto.setRazonSocial(entity.getRazonSocial());
        dto.setRuc(entity.getRuc());
        dto.setDireccion(entity.getDireccion());
        dto.setTelefono(entity.getTelefono());
        dto.setEmail(entity.getEmail());
        dto.setEstado(entity.getEstado());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaModificacion(entity.getFechaModificacion());
        return dto;
    }

public static Proveedores toEntity(ProveedoresDTO dto) {
    if (dto == null) return null;
    Proveedores entity = new Proveedores();
    entity.setIdProveedor(dto.getIdProveedor());
    entity.setRazonSocial(dto.getRazonSocial());
    entity.setRuc(dto.getRuc());
    entity.setDireccion(dto.getDireccion());
    entity.setTelefono(dto.getTelefono());
    entity.setEmail(dto.getEmail());
    entity.setEstado(dto.getEstado());
    entity.setFechaCreacion(dto.getFechaCreacion()); 
    
    return entity;
}
}
