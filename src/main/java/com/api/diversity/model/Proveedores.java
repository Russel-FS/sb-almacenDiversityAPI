package com.api.diversity.model;

import lombok.Data;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "Proveedores")
public class Proveedores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Proveedor")
    private Long idProveedor;

    @Column(name = "Razon_Social", nullable = false)
    private String razonSocial;

    @Column(name = "RUC", nullable = false)
    private String ruc;

    @Column(name = "Direccion")
    private String direccion;

    @Column(name = "Telefono")
    private String telefono;

    @Column(name = "Email")
    private String email;

    @Column(name = "Estado")
    private String estado;

    @Column(name = "Fecha_Creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "Fecha_Modificacion")
    private LocalDateTime fechaModificacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaModificacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaModificacion = LocalDateTime.now();
    }

    public enum EstadoProveedor {
        Activo, Inactivo
    }

}
