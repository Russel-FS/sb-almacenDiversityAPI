package com.api.diversity.mappers;

import org.springframework.stereotype.Component;
import com.api.diversity.dtos.CategoriaDto;
import com.api.diversity.model.Categoria;

@Component
public class CategoriaMapper {

    public CategoriaDto toDto(Categoria categoria) {
        CategoriaDto dto = new CategoriaDto();
        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNombreCategoria(categoria.getNombreCategoria());
        dto.setDescripcion(categoria.getDescripcion());
        dto.setEstado(categoria.getEstado());
        dto.setFechaCreacion(categoria.getFechaCreacion());
        dto.setFechaModificacion(categoria.getFechaModificacion());

        if (categoria.getRubro() != null) {
            dto.setIdRubro(categoria.getRubro().getIdRubro());
            dto.setNombreRubro(categoria.getRubro().getNombre());
        }

        if (categoria.getCreatedBy() != null) {
            dto.setCreatedBy(categoria.getCreatedBy().getIdUsuario());
            dto.setNombreCreatedBy(categoria.getCreatedBy().getNombreCompleto());
        }

        if (categoria.getUpdatedBy() != null) {
            dto.setUpdatedBy(categoria.getUpdatedBy().getIdUsuario());
            dto.setNombreUpdatedBy(categoria.getUpdatedBy().getNombreCompleto());
        }

        return dto;
    }
}