package com.api.diversity.service;

import com.api.diversity.model.Rol;
import com.api.diversity.model.Rubro;
import com.api.diversity.model.Usuario;
import com.api.diversity.repository.RolRepository;
import com.api.diversity.repository.RubroRepository;
import com.api.diversity.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.api.diversity.controller.UsuarioResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private RubroRepository rubroRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 

     public List<UsuarioResponseDTO> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        
        return usuarios.stream()
                .map(this::convertirAUsuarioResponseDTO) 
                .collect(Collectors.toList());
    }

       private UsuarioResponseDTO convertirAUsuarioResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
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

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario registrar(Usuario usuario) {
        
        Rol rol = rolRepository.findById(usuario.getRol().getIdRol())
                .orElseThrow(
                        () -> new EntityNotFoundException("Rol no encontrado con ID: " + usuario.getRol().getIdRol()));
        Rubro rubro = rubroRepository.findById(usuario.getRubro().getIdRubro())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Rubro no encontrado con ID: " + usuario.getRubro().getIdRubro()));

        usuario.setRol(rol);
        usuario.setRubro(rubro);

        if (usuario.getEstado() == null) {
            usuario.setEstado("Activo");
        }

      
        String contrasenaCodificada = passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(contrasenaCodificada);

        return usuarioRepository.save(usuario);
    }

    public Usuario editar(Long id, Usuario usuarioDetails) {
        
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));

        

        
        if (usuarioDetails.getNombreUsuario() != null) {
            usuario.setNombreUsuario(usuarioDetails.getNombreUsuario());
        }
        if (usuarioDetails.getEmail() != null) {
            usuario.setEmail(usuarioDetails.getEmail());
        }
        if (usuarioDetails.getNombreCompleto() != null) {
            usuario.setNombreCompleto(usuarioDetails.getNombreCompleto());
        }
        if (usuarioDetails.getUrlImagen() != null) {
            usuario.setUrlImagen(usuarioDetails.getUrlImagen());
        }
        if (usuarioDetails.getPublicId() != null) {
            usuario.setPublicId(usuarioDetails.getPublicId());
        }
        if (usuarioDetails.getEstado() != null) {
            usuario.setEstado(usuarioDetails.getEstado());
        }

       
        if (usuarioDetails.getContrasena() != null && !usuarioDetails.getContrasena().trim().isEmpty()) {
            usuario.setContrasena(passwordEncoder.encode(usuarioDetails.getContrasena()));
        }

       
        if (usuarioDetails.getRol() != null && usuarioDetails.getRol().getIdRol() != null) {
            Rol rol = rolRepository.findById(usuarioDetails.getRol().getIdRol())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Rol no encontrado con ID: " + usuarioDetails.getRol().getIdRol()));
            usuario.setRol(rol);
        }

        if (usuarioDetails.getRubro() != null && usuarioDetails.getRubro().getIdRubro() != null) {
            Rubro rubro = rubroRepository.findById(usuarioDetails.getRubro().getIdRubro())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Rubro no encontrado con ID: " + usuarioDetails.getRubro().getIdRubro()));
            usuario.setRubro(rubro);
        }

        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar. Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}