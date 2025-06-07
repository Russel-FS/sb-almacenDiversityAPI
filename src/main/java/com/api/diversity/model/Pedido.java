package com.api.diversity.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Pedido {
    private Integer idPedido;
    private Cliente cliente;
    private LocalDateTime fechaPedido;
    private BigDecimal totalPedido;
    private String estadoPedido;
    private Usuario usuario;
    private List<DetallePedido> detalles;
}