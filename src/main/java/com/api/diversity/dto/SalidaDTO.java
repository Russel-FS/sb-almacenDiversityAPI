package com.api.diversity.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SalidaDTO {
    private Long idSalida;
    private String numeroDocumento;
    private String tipoDocumento;
    private LocalDateTime fechaSalida;
    private String motivoSalida;
    private BigDecimal totalVenta;
    private String estado;
    private Long idUsuarioRegistro;
    private Long idUsuarioAprobacion;
    private LocalDateTime fechaAprobacion;
    private String observaciones;
}
