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

    @Column(name = "Fecha_Salida")
    private LocalDateTime fechaSalida;

    @Column(name = "Motivo_Salida")
    private String motivoSalida;

    @Column(name = "Total_Venta", nullable = false)
    private BigDecimal totalVenta;

    @Column(name = "Estado")
    private String estado;

    @Column(name = "ID_Usuario_Registro", nullable = false)
    private Long idUsuarioRegistro;

    @Column(name = "ID_Usuario_Aprobacion")
    private Long idUsuarioAprobacion;

    @Column(name = "Fecha_Aprobacion")
    private LocalDateTime fechaAprobacion;

    @Column(name = "Observaciones")
    private String observaciones;
}