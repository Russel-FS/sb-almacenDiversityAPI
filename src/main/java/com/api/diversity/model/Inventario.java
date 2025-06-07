package com.api.diversity.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Inventario {
    private Integer idInventario;
    private Producto producto;
    private Integer stockInicial;
    private Integer totalEntradas;
    private Integer totalSalidas;
    private Integer stockActual;
    private LocalDateTime ultimaActualizacion;
}