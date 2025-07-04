package com.api.diversity.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EntradaDto {
    private Long idEntrada;
    private String numeroFactura;
    private Long idProveedor;
    private String razonSocialProveedor;
    private LocalDateTime fechaEntrada;
    private BigDecimal costoTotal;
    private String estado;
    private Long idUsuarioRegistro;
    private String nombreUsuarioRegistro;
    private Long idUsuarioAprobacion;
    private String nombreUsuarioAprobacion;
    private LocalDateTime fechaAprobacion;
    private String observaciones;
}