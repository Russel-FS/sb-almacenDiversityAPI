package com.api.diversity.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Salidas")
@Data
public class Salida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Salida")
    private Long idSalida;

    @Column(name = "Numero_Documento", nullable = false, length = 50)
    private String numeroDocumento;

    @Enumerated(EnumType.STRING)
    @Column(name = "Tipo_Documento", nullable = false, length = 20, columnDefinition = "ENUM('Boleta', 'Factura', 'Nota de Venta')")
    private TipoDocumento tipoDocumento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Cliente")
    private Cliente cliente;

    @Column(name = "Fecha_Salida")
    private LocalDateTime fechaSalida;

    @Column(name = "Motivo_Salida", length = 100)
    private String motivoSalida;

    @Column(name = "Total_Venta", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalVenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "Estado", length = 15, columnDefinition = "ENUM('Pendiente', 'Completado', 'Anulado')")
    private EstadoSalida estado;

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

    @OneToMany(mappedBy = "salida", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleSalida> detalles;

    public enum TipoDocumento {
        Boleta, Factura, Nota_de_Venta
    }

    public enum EstadoSalida {
        Pendiente, Completado, Anulado
    }
}