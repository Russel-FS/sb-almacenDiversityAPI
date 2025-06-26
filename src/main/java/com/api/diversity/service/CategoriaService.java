package com.api.diversity.service;

import com.api.diversity.controller.CategoriaDTO;
import com.api.diversity.model.Categoria;
import com.api.diversity.model.Rubro;
import com.api.diversity.model.Usuario;
import com.api.diversity.repository.CategoriaRepository;
import com.api.diversity.repository.RubroRepository;
import com.api.diversity.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private RubroRepository rubroRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public CategoriaDTO registrarCategoria(CategoriaDTO dto) {
        Rubro rubro = rubroRepository.findById(dto.getIdRubro())
                .orElseThrow(() -> new EntityNotFoundException("Rubro no encontrado con ID: " + dto.getIdRubro()));
        Usuario creador = usuarioRepository.findById(dto.getCreatedById())
                .orElseThrow(() -> new EntityNotFoundException("Usuario creador no encontrado con ID: " + dto.getCreatedById()));

        Categoria categoria = new Categoria();
        categoria.setNombreCategoria(dto.getNombreCategoria());
        categoria.setDescripcion(dto.getDescripcion());
        categoria.setRubro(rubro);
        categoria.setCreatedBy(creador);
        categoria.setEstado("Activo");

        Categoria nuevaCategoria = categoriaRepository.save(categoria);
        return convertirADTO(nuevaCategoria);
    }

    public CategoriaDTO editarCategoria(Long id, CategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + id));
        Usuario actualizador = usuarioRepository.findById(dto.getUpdatedById())
                .orElseThrow(() -> new EntityNotFoundException("Usuario actualizador no encontrado con ID: " + dto.getUpdatedById()));
        
        categoria.setNombreCategoria(dto.getNombreCategoria());
        categoria.setDescripcion(dto.getDescripcion());
        categoria.setEstado(dto.getEstado());
        categoria.setUpdatedBy(actualizador);
        
        if (dto.getIdRubro() != null && !dto.getIdRubro().equals(categoria.getRubro().getIdRubro())) {
            Rubro nuevoRubro = rubroRepository.findById(dto.getIdRubro())
                .orElseThrow(() -> new EntityNotFoundException("Rubro no encontrado con ID: " + dto.getIdRubro()));
            categoria.setRubro(nuevoRubro);
        }
        
        Categoria categoriaActualizada = categoriaRepository.save(categoria);
        return convertirADTO(categoriaActualizada);
    }

    public void eliminarCategoria(Long id, Long updatedById) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + id));
        Usuario actualizador = usuarioRepository.findById(updatedById)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + updatedById));

        categoria.setEstado("Eliminado");
        categoria.setUpdatedBy(actualizador);
        categoriaRepository.save(categoria);
    }

    public List<CategoriaDTO> listarTodasLasCategorias() {
        return categoriaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    public CategoriaDTO buscarCategoriaPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + id));
        return convertirADTO(categoria);
    }

    private CategoriaDTO convertirADTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNombreCategoria(categoria.getNombreCategoria());
        dto.setDescripcion(categoria.getDescripcion());
        dto.setEstado(categoria.getEstado());
        dto.setFechaCreacion(categoria.getFechaCreacion());
        dto.setFechaModificacion(categoria.getFechaModificacion());
        
        if (categoria.getRubro() != null) {
            dto.setIdRubro(categoria.getRubro().getIdRubro());
            dto.setNombreRubro(categoria.getRubro().getNombre());
        }
        if (categoria.getCreatedBy() != null) {
            dto.setCreatedById(categoria.getCreatedBy().getIdUsuario());
            dto.setCreatedByNombreUsuario(categoria.getCreatedBy().getNombreUsuario());
        }
        if (categoria.getUpdatedBy() != null) {
            dto.setUpdatedById(categoria.getUpdatedBy().getIdUsuario());
            dto.setUpdatedByNombreUsuario(categoria.getUpdatedBy().getNombreUsuario());
        }
        return dto;
    }
}