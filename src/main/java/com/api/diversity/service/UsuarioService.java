package com.api.diversity.service;

import com.api.diversity.model.Rol;
import com.api.diversity.model.Rubro;
import com.api.diversity.model.Usuario;
import com.api.diversity.repository.RolRepository;
import com.api.diversity.repository.RubroRepository;
import com.api.diversity.repository.UsuarioRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.diversity.dtos.UsuarioDto;
import com.api.diversity.mappers.UsuarioMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private RubroRepository rubroRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UsuarioMapper mapper;

    public List<UsuarioDto> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDto> buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(mapper::toDto);
    }

    public UsuarioDto registrar(Usuario usuario) {

        Rol rol = rolRepository.findById(usuario.getRol().getIdRol())
                .orElseThrow(
                        () -> new EntityNotFoundException("Rol no encontrado con ID: " + usuario.getRol().getIdRol()));
        Rubro rubro = rubroRepository.findById(usuario.getRubro().getIdRubro())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Rubro no encontrado con ID: " + usuario.getRubro().getIdRubro()));

        // validacion de nombre y email
        if (usuarioRepository.existsByEmail(
                usuario.getEmail()) && usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario())) {
            throw new EntityExistsException("nombre de usuario no disponible o el email ya existe");
        }

        usuario.setRol(rol);
        usuario.setRubro(rubro);

        if (usuario.getEstado() == null) {
            usuario.setEstado("Activo");
        }

        String contrasenaCodificada = passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(contrasenaCodificada);

        return mapper.toDto(usuarioRepository.save(usuario));
    }

    public UsuarioDto editar(Long id, Usuario usuarioDetails) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));

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
        usuario.setEmail(usuarioDetails.getEmail());// email de usuario no modificable
        usuario.setNombreUsuario(usuarioDetails.getNombreUsuario());// campos no modificables
        return mapper.toDto(usuarioRepository.save(usuario));
    }

    public void eliminar(Long id) {
        usuarioRepository.findById(id).ifPresent(usuario -> {
            usuario.setEstado("Inactivo");
            usuarioRepository.save(usuario);
        });
    }
}