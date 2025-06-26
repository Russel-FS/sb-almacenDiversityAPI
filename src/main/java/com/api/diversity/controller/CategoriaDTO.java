package com.api.diversity.controller;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CategoriaDTO {
    private Long idCategoria;
    private String nombreCategoria;
    private String descripcion;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    
    private Long idRubro;
    private String nombreRubro;

    private Long createdById;
    private String createdByNombreUsuario;

    private Long updatedById;
    private String updatedByNombreUsuario;
}