package com.api.diversity.dtos;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProveedorDto {
    private Long idProveedor;
    private String razonSocial;
    private String ruc;
    private String direccion;
    private String telefono;
    private String email;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}