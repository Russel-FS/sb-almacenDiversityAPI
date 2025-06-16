package com.api.diversity.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "Roles")
@Data
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Rol")
    private Long idRol;

    @Column(name = "Nombre_Rol", nullable = false, unique = true, length = 50)
    private String nombreRol;

    @Column(name = "Descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "Estado", length = 8, columnDefinition = "ENUM('Activo', 'Inactivo')")
    private String estado;

    @CreationTimestamp
    @Column(name = "Fecha_Creacion", updatable = false)
    private LocalDateTime fechaCreacion;
}