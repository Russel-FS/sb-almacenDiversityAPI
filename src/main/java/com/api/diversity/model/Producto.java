package com.api.diversity.model;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Producto")
    private Long idProducto;

    @Column(name = "Nombre_Producto", nullable = false)
    private String nombre;

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "ID_Categoria", nullable = false)
    private Integer categoriaId;

    @Column(name = "Stock_Actual")
    private Integer stock;

    @Column(name = "Precio_Venta")
    private BigDecimal precioUnitario;

    @Column(name = "Fecha_Creacion")
    private LocalDateTime fechaRegistro;
}