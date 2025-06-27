package com.api.diversity.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DetalleSalidaDTO {
    private Long idDetalleSalida;
    private Long idSalida;
    private Long idProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private Long idUsuarioRegistro;
    private LocalDateTime fechaRegistro;
    private String estado;
}
