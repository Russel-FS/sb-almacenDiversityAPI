package com.api.diversity.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class DetalleEntradaDto {
    private Long idDetalleEntrada;
    private Long idEntrada;
    private Long idProducto;
    private String codigoProducto;
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private Long idUsuarioRegistro;
    private String nombreUsuarioRegistro;
    private LocalDateTime fechaRegistro;
    private String estado;
}