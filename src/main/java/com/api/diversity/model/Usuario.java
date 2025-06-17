package com.api.diversity.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "Usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Usuario")
    private Long idUsuario;

    @Column(name = "Nombre_Usuario", nullable = false, unique = true, length = 50)
    private String nombreUsuario;

    @Column(name = "Email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "Nombre_Completo", nullable = false, length = 100)
    private String nombreCompleto;

    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "ID_Rol", nullable = false) 
    private Rol rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Rubro", nullable = false) 
    private Rubro rubro;

    @Column(name = "Contraseña", nullable = false, length = 255)
    private String contrasena;

    @Column(name = "UrlImagen", length = 255) 
    private String urlImagen;

    @Column(name = "PublicId", length = 100)  
    private String publicId;

    @Column(name = "Estado", length = 10, columnDefinition = "ENUM('Activo', 'Inactivo', 'Bloqueado')")
    private String estado;

    @Column(name = "Ultimo_Acceso")
    private LocalDateTime ultimoAcceso;

    @CreationTimestamp
    @Column(name = "Fecha_Creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "Fecha_Modificacion")
    private LocalDateTime fechaModificacion;

   
}