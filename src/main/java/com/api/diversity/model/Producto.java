package com.api.diversity.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Producto {
    private String idProducto;
    private String nombre;
    private String descripcion;
    private Categoria categoria;
    private Integer stock;
    private BigDecimal precioUnitario;
    private LocalDateTime fechaRegistro;
}