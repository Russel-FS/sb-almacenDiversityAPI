package com.api.diversity.model;

import lombok.Data;
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
}
