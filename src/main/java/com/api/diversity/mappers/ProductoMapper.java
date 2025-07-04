package com.api.diversity.mappers;

import org.springframework.stereotype.Component;
import com.api.diversity.dtos.ProductoDto;
import com.api.diversity.model.Producto;

@Component
public class ProductoMapper {

    public ProductoDto toDto(Producto producto) {
        ProductoDto dto = new ProductoDto();
        dto.setIdProducto(producto.getIdProducto());
        dto.setCodigoProducto(producto.getCodigoProducto());
        dto.setNombreProducto(producto.getNombreProducto());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecioCompra(producto.getPrecioCompra());
        dto.setPrecioVenta(producto.getPrecioVenta());
        dto.setStockActual(producto.getStockActual());
        dto.setStockMinimo(producto.getStockMinimo());
        dto.setStockMaximo(producto.getStockMaximo());
        dto.setUrlImagen(producto.getUrlImagen());
        dto.setPublicId(producto.getPublicId());
        dto.setEstado(producto.getEstado());
        dto.setFechaCreacion(producto.getFechaCreacion());
        dto.setFechaModificacion(producto.getFechaModificacion());

        if (producto.getCategoria() != null) {
            dto.setIdCategoria(producto.getCategoria().getIdCategoria());
            dto.setNombreCategoria(producto.getCategoria().getNombreCategoria());

            if (producto.getCategoria().getRubro() != null) {
                dto.setIdRubro(producto.getCategoria().getRubro().getIdRubro());
                dto.setNombreRubro(producto.getCategoria().getRubro().getNombre());
            }
        }

        if (producto.getCreatedBy() != null) {
            dto.setCreatedBy(producto.getCreatedBy().getIdUsuario());
            dto.setNombreCreatedBy(producto.getCreatedBy().getNombreCompleto());
        }

        if (producto.getUpdatedBy() != null) {
            dto.setUpdatedBy(producto.getUpdatedBy().getIdUsuario());
            dto.setNombreUpdatedBy(producto.getUpdatedBy().getNombreCompleto());
        }

        return dto;
    }
}