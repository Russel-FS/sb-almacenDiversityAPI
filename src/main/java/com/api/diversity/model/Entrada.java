package com.api.diversity.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Entradas")
@Data
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Entrada")
    private Long idEntrada;

    @Column(name = "Numero_Factura", nullable = false, length = 50)
    private String numeroFactura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Proveedor", nullable = false)
    private Proveedor proveedor;

    @Column(name = "Fecha_Entrada")
    private LocalDateTime fechaEntrada;

    @Column(name = "Costo_Total", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "Estado", length = 15, columnDefinition = "ENUM('Pendiente', 'Completado', 'Anulado')")
    private EstadoEntrada estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Usuario_Registro", nullable = false)
    private Usuario usuarioRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Usuario_Aprobacion")
    private Usuario usuarioAprobacion;

    @Column(name = "Fecha_Aprobacion")
    private LocalDateTime fechaAprobacion;

    @Column(name = "Observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @OneToMany(mappedBy = "entrada", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleEntrada> detalles;

    public enum EstadoEntrada {
        Pendiente, Completado, Anulado
    }
}