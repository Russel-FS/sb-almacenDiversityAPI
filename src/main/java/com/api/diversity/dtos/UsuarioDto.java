package com.api.diversity.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UsuarioDto {
    private Long idUsuario;
    private String nombreUsuario;
    private String email;
    private String nombreCompleto;
    private String urlImagen;
    private String estado;
    private LocalDateTime ultimoAcceso;

    private long ID_Rol;
    private long ID_Rubro;

    private String nombreRol;
    private String nombreRubro;

}
