package com.api.diversity.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "Rubros")
@Data
public class Rubro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Rubro")
    private Long idRubro;

    @Column(name = "Nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name = "Code", nullable = false, length = 50)
    private String code;

    @Column(name = "Descripcion", length = 255)
    private String descripcion;

    @Column(name = "Estado", length = 8, columnDefinition = "ENUM('Activo', 'Inactivo')")
    private String estado;

    @Column(name = "PublicId", length = 100)
    private String publicId;

    @Column(name = "ImagenUrl", length = 255)
    private String imagenUrl;

    @CreationTimestamp
    @Column(name = "Fecha_Creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "Fecha_Modificacion")
    private LocalDateTime fechaModificacion;
}