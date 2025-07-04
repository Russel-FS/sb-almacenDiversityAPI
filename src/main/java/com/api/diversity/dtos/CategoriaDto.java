package com.api.diversity.dtos;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CategoriaDto {
    private Long idCategoria;
    private Long idRubro;
    private String nombreRubro;
    private String nombreCategoria;
    private String descripcion;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private Long createdBy;
    private String nombreCreatedBy;
    private Long updatedBy;
    private String nombreUpdatedBy;
}