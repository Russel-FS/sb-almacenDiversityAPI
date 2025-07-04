package com.api.diversity.dtos;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ClienteDto {
    private Long idCliente;
    private String nombreCliente;
    private String tipoDocumento;
    private String numeroDocumento;
    private String direccion;
    private String telefono;
    private String email;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}