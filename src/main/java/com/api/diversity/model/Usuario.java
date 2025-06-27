package com.api.diversity.model;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "Usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Usuario")
    private Integer idUsuario;

    @Column(name = "Nombre_Usuario", nullable = false)
    private String nombreUsuario;

    @Column(name = "rol")
    private String rol;

    @Column(name = "Contraseña", nullable = false)
    private String contraseña;
}