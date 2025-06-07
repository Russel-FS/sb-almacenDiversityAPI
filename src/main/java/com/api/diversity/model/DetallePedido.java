package com.api.diversity.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetallePedido {
    private Integer idDetalle;
    private Pedido pedido;
    private Producto producto;
    private Integer cantidad;
    private BigDecimal subtotal;
}