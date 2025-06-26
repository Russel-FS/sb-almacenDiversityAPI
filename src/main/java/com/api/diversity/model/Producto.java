package com.api.diversity.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "Productos")
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Producto")
    private Long idProducto;

    @Column(name = "Codigo_Producto", nullable = false, unique = true, length = 50)
    private String codigoProducto;

    @Column(name = "Nombre_Producto", nullable = false, length = 100)
    private String nombreProducto;

    @Column(name = "Descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Categoria", nullable = false)
    private Categoria categoria;

    @Column(name = "Precio_Compra", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioCompra;

    @Column(name = "Precio_Venta", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioVenta;

    @Column(name = "Stock_Actual", nullable = false)
    private Integer stockActual = 0;

    @Column(name = "Stock_Minimo", nullable = false)
    private Integer stockMinimo = 0;
    
    @Column(name = "Stock_Maximo", nullable = false)
    private Integer stockMaximo = 0;

    @Column(name = "url_imagen", length = 255)
    private String urlImagen;

    @Column(name = "public_id", length = 100)
    private String publicId;

    @Column(name = "Estado", length = 10, columnDefinition = "ENUM('Activo', 'Inactivo', 'Agotado', 'Eliminado')")
    private String estado;

    @CreationTimestamp
    @Column(name = "Fecha_Creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "Fecha_Modificacion")
    private LocalDateTime fechaModificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private Usuario createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private Usuario updatedBy;
}