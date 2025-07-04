package com.api.diversity.service;

import com.api.diversity.model.Categoria;
import com.api.diversity.model.Rubro;
import com.api.diversity.model.Usuario;
import com.api.diversity.repository.CategoriaRepository;
import com.api.diversity.repository.RubroRepository;
import com.api.diversity.repository.UsuarioRepository;
import com.api.diversity.dtos.CategoriaDto;
import com.api.diversity.mappers.CategoriaMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private RubroRepository rubroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final CategoriaMapper mapper;

    public List<CategoriaDto> listarTodos() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CategoriaDto> listarPorRubro(Long idRubro) {
        List<Categoria> categorias = categoriaRepository.findByRubroIdRubro(idRubro);
        return categorias.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<CategoriaDto> buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(mapper::toDto);
    }

    public CategoriaDto registrar(Categoria categoria) {
        Rubro rubro = rubroRepository.findById(categoria.getRubro().getIdRubro())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Rubro no encontrado con ID: " + categoria.getRubro().getIdRubro()));

        Usuario usuario = usuarioRepository.findById(categoria.getCreatedBy().getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuario no encontrado con ID: " + categoria.getCreatedBy().getIdUsuario()));

        // Validar que no exista una categoría con el mismo nombre en el mismo rubro
        if (categoriaRepository.existsByNombreCategoriaAndRubroIdRubro(
                categoria.getNombreCategoria(), rubro.getIdRubro())) {
            throw new EntityExistsException("Ya existe una categoría con este nombre en el rubro seleccionado");
        }

        categoria.setRubro(rubro);
        categoria.setCreatedBy(usuario);

        if (categoria.getEstado() == null) {
            categoria.setEstado("Activo");
        }

        return mapper.toDto(categoriaRepository.save(categoria));
    }

    public CategoriaDto editar(Long id, Categoria categoriaDetails) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + id));

        if (categoriaDetails.getNombreCategoria() != null) {
            categoria.setNombreCategoria(categoriaDetails.getNombreCategoria());
        }
        if (categoriaDetails.getDescripcion() != null) {
            categoria.setDescripcion(categoriaDetails.getDescripcion());
        }
        if (categoriaDetails.getEstado() != null) {
            categoria.setEstado(categoriaDetails.getEstado());
        }

        if (categoriaDetails.getRubro() != null && categoriaDetails.getRubro().getIdRubro() != null) {
            Rubro rubro = rubroRepository.findById(categoriaDetails.getRubro().getIdRubro())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Rubro no encontrado con ID: " + categoriaDetails.getRubro().getIdRubro()));
            categoria.setRubro(rubro);
        }

        if (categoriaDetails.getUpdatedBy() != null && categoriaDetails.getUpdatedBy().getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(categoriaDetails.getUpdatedBy().getIdUsuario())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Usuario no encontrado con ID: " + categoriaDetails.getUpdatedBy().getIdUsuario()));
            categoria.setUpdatedBy(usuario);
        }

        return mapper.toDto(categoriaRepository.save(categoria));
    }

    public void eliminar(Long id) {
        categoriaRepository.findById(id).ifPresent(categoria -> {
            categoria.setEstado("Eliminado");
            categoriaRepository.save(categoria);
        });
    }
}