package com.api.diversity.model;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class DetalleSalida {
    private Long idDetalleSalida;
    private Long idSalida;
    private Long idProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private Long idUsuarioRegistro;
    private Timestamp fechaRegistro;
    private String estado;
}