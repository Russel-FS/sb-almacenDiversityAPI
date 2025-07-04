package com.api.diversity.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "Clientes")
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Cliente")
    private Long idCliente;

    @Column(name = "Nombre_Cliente", nullable = false, length = 100)
    private String nombreCliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "Tipo_Documento", nullable = false, length = 20)
    private TipoDocumento tipoDocumento;

    @Column(name = "Numero_Documento", nullable = false, length = 20)
    private String numeroDocumento;

    @Column(name = "Direccion", columnDefinition = "TEXT")
    private String direccion;

    @Column(name = "Telefono", length = 20)
    private String telefono;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "Estado", length = 10, columnDefinition = "ENUM('Activo', 'Inactivo')")
    private String estado;

    @CreationTimestamp
    @Column(name = "Fecha_Creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "Fecha_Modificacion")
    private LocalDateTime fechaModificacion;

    public enum TipoDocumento {
        DNI, RUC, CE, PASAPORTE
    }
}