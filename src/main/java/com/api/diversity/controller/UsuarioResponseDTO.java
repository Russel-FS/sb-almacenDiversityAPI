package com.api.diversity.controller;

import java.time.LocalDateTime;

public class UsuarioResponseDTO {

    private Long idUsuario;
    private String nombreUsuario;
    private String email;
    private String nombreCompleto;
    private String urlImagen;
    private String estado;
    private LocalDateTime ultimoAcceso;
    
    public Long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    public String getUrlImagen() {
        return urlImagen;
    }
    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }
    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }
    private long ID_Rol;
    private long ID_Rubro;
   
   
    public long getID_Rol() {
        return ID_Rol;
    }
    public void setID_Rol(long iD_Rol) {
        ID_Rol = iD_Rol;
    }
    public long getID_Rubro() {
        return ID_Rubro;
    }
    public void setID_Rubro(long iD_Rubro) {
        ID_Rubro = iD_Rubro;
    }
    private String nombreRol;
    private String nombreRubro;


    public String getNombreRol() {
        return nombreRol;
    }
    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
    public String getNombreRubro() {
        return nombreRubro;
    }
    public void setNombreRubro(String nombreRubro) {
        this.nombreRubro = nombreRubro;
    }

}
