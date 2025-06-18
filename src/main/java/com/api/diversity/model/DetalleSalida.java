package com.api.diversity.model;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Detalle_Salida")
public class DetalleSalida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Detalle_Salida")
    private Long idDetalleSalida;

    @Column(name = "ID_Salida", nullable = false)
    private Long idSalida;

    @Column(name = "ID_Producto", nullable = false)
    private Long idProducto;

    @Column(name = "Cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "Precio_Unitario", nullable = false)
    private BigDecimal precioUnitario;

    @Column(name = "Subtotal", nullable = false)
    private BigDecimal subtotal;

    @Column(name = "ID_Usuario_Registro", nullable = false)
    private Long idUsuarioRegistro;

    @Column(name = "Fecha_Registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "Estado")
    private String estado;
}