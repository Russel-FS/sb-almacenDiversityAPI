package com.api.diversity.mappers;

import org.springframework.stereotype.Component;

import com.api.diversity.dtos.UsuarioDto;
import com.api.diversity.model.Usuario;

@Component
public class UsuarioMapper {

    public UsuarioDto toDto(Usuario usuario) {
        UsuarioDto dto = new UsuarioDto();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setEmail(usuario.getEmail());
        dto.setNombreCompleto(usuario.getNombreCompleto());
        dto.setUrlImagen(usuario.getUrlImagen());
        dto.setEstado(usuario.getEstado());
        dto.setUltimoAcceso(usuario.getUltimoAcceso());

        if (usuario.getRol() != null) {
            dto.setNombreRol(usuario.getRol().getNombreRol());
            dto.setID_Rol(usuario.getRol().getIdRol());
        }
        if (usuario.getRubro() != null) {
            dto.setNombreRubro(usuario.getRubro().getNombre());
            dto.setID_Rubro(usuario.getRubro().getIdRubro());
        }

        return dto;
    }
}
