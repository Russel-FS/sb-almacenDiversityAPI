package com.api.diversity.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SalidaDto {
    private Long idSalida;
    private String numeroDocumento;
    private String tipoDocumento;
    private Long idCliente;
    private String nombreCliente;
    private LocalDateTime fechaSalida;
    private String motivoSalida;
    private BigDecimal totalVenta;
    private String estado;
    private Long idUsuarioRegistro;
    private String nombreUsuarioRegistro;
    private Long idUsuarioAprobacion;
    private String nombreUsuarioAprobacion;
    private LocalDateTime fechaAprobacion;
    private String observaciones;
}