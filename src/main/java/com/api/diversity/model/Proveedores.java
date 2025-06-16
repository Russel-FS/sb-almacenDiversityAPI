package com.api.diversity.model;

import lombok.Data;

@Data
public class Proveedores {
    private Long idProveedor;
    private String razonSocial;
    private String ruc;
    private String direccion;
    private String telefono;
    private String email;
    private String estado;
}
