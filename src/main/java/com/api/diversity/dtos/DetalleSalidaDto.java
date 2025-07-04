package com.api.diversity.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class DetalleSalidaDto {
    private Long idDetalleSalida;
    private Long idSalida;
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