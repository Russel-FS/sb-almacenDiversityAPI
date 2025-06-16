package com.api.diversity.model;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class Entrada {
    private Long idEntrada;
    private String numeroFactura;
    private Long idProveedor;
    private Timestamp fechaEntrada;
    private BigDecimal costoTotal;
    private String estado;
    private Long idUsuarioRegistro;
    private Long idUsuarioAprobacion;
    private Timestamp fechaAprobacion;
    private String observaciones;
}