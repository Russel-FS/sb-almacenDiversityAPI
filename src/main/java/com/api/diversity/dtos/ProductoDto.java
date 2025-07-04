package com.api.diversity.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProductoDto {
    private Long idProducto;
    private String codigoProducto;
    private String nombreProducto;
    private String descripcion;
    private Long idCategoria;
    private String nombreCategoria;
    private Long idRubro;
    private String nombreRubro;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private Integer stockActual;
    private Integer stockMinimo;
    private Integer stockMaximo;
    private String urlImagen;
    private String publicId;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private Long createdBy;
    private String nombreCreatedBy;
    private Long updatedBy;
    private String nombreUpdatedBy;
}