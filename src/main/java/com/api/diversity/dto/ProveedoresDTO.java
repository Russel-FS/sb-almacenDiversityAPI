package com.api.diversity.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProveedoresDTO {
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
