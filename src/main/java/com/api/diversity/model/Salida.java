package com.api.diversity.model;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Salidas")
public class Salida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Salida")
    private Long idSalida;

    @Column(name = "Numero_Documento", nullable = false)
    private String numeroDocumento;

    @Column(name = "Tipo_Documento", nullable = false)
    private String tipoDocumento;

    @Column(name = "Fecha_Salida", updatable = false, insertable = false)
    private LocalDateTime fechaSalida;

    @Column(name = "Motivo_Salida")
    private String motivoSalida;

    @Column(name = "Total_Venta", nullable = false)
    private BigDecimal totalVenta;

    @Column(name = "Estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "ID_Usuario_Registro", nullable = false)
    private Usuario usuarioRegistro;

    @ManyToOne
    @JoinColumn(name = "ID_Usuario_Aprobacion")
    private Usuario usuarioAprobacion;

    @Column(name = "Fecha_Aprobacion")
    private LocalDateTime fechaAprobacion;

    @Column(name = "Observaciones")
    private String observaciones;
}