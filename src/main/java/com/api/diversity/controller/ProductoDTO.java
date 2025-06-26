package com.api.diversity.controller;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductoDTO {
    private Long idProducto;
    private String codigoProducto;
    private String nombreProducto;
    private String descripcion;
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

    private Long idCategoria;
    private String nombreCategoria;
    
    private Long createdById;
    private String createdByNombreUsuario;

    private Long updatedById;
    private String updatedByNombreUsuario;
}