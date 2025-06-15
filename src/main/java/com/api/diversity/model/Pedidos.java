package com.api.diversity.model;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class Pedidos {
    private Integer idPedido;
    private Integer clienteId;
    private Timestamp fechaPedido;
    private BigDecimal totalPedido;
    private String estadoPedido;
    private Integer usuarioId;
    private List<DetallePedido> detalles;
}