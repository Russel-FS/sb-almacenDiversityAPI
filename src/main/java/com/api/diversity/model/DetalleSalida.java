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

    @ManyToOne
    @JoinColumn(name = "ID_Salida", nullable = false)
    private Salida salida;

    @ManyToOne
    @JoinColumn(name = "ID_Producto", nullable = false)
    private Producto producto;

    @Column(name = "Cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "Precio_Unitario", nullable = false)
    private BigDecimal precioUnitario;

    @Column(name = "Subtotal", nullable = false)
    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "ID_Usuario_Registro", nullable = false)
    private Usuario usuarioRegistro;

    @Column(name = "Fecha_Registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "Estado")
    private String estado;
}