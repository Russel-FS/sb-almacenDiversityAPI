package com.api.diversity.model;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class Salida {
    private Long idSalida;
    private String numeroDocumento;
    private String tipoDocumento;
    private Timestamp fechaSalida;
    private String motivoSalida;
    private BigDecimal totalVenta;
    private String estado;
    private Long idUsuarioRegistro;
    private Long idUsuarioAprobacion;
    private Timestamp fechaAprobacion;
    private String observaciones;
}