package com.api.diversity.service;

import com.api.diversity.controller.ProductoDTO;
import com.api.diversity.model.Categoria;
import com.api.diversity.model.Producto;
import com.api.diversity.model.Usuario;
import com.api.diversity.repository.CategoriaRepository;
import com.api.diversity.repository.ProductoRepository;
import com.api.diversity.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public ProductoDTO registrarProducto(ProductoDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + dto.getIdCategoria()));
        Usuario creador = usuarioRepository.findById(dto.getCreatedById())
                .orElseThrow(() -> new EntityNotFoundException("Usuario creador no encontrado con ID: " + dto.getCreatedById()));

        Producto producto = new Producto();
        fromDTO(producto, dto);
        producto.setCategoria(categoria);
        producto.setCreatedBy(creador);
        producto.setEstado("Activo");
        
        Producto nuevoProducto = productoRepository.save(producto);
        return convertirADTO(nuevoProducto);
    }

    public ProductoDTO editarProducto(Long id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id));
        Usuario actualizador = usuarioRepository.findById(dto.getUpdatedById())
                .orElseThrow(() -> new EntityNotFoundException("Usuario actualizador no encontrado con ID: " + dto.getUpdatedById()));

        fromDTO(producto, dto);
        producto.setUpdatedBy(actualizador);
        
        if(dto.getIdCategoria() != null && !dto.getIdCategoria().equals(producto.getCategoria().getIdCategoria())) {
            Categoria nuevaCategoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + dto.getIdCategoria()));
            producto.setCategoria(nuevaCategoria);
        }
        
        Producto productoActualizado = productoRepository.save(producto);
        return convertirADTO(productoActualizado);
    }

    public void eliminarProducto(Long id, Long updatedById) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id));
        Usuario actualizador = usuarioRepository.findById(updatedById)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + updatedById));

        producto.setEstado("Eliminado");
        producto.setUpdatedBy(actualizador);
        productoRepository.save(producto);
    }

    public List<ProductoDTO> listarTodosLosProductos() {
        return productoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    public ProductoDTO buscarProductoPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id));
        return convertirADTO(producto);
    }
    
    private void fromDTO(Producto producto, ProductoDTO dto) {
        producto.setCodigoProducto(dto.getCodigoProducto());
        producto.setNombreProducto(dto.getNombreProducto());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecioCompra(dto.getPrecioCompra());
        producto.setPrecioVenta(dto.getPrecioVenta());
        producto.setStockActual(dto.getStockActual());
        producto.setStockMinimo(dto.getStockMinimo());
        producto.setStockMaximo(dto.getStockMaximo());
        producto.setUrlImagen(dto.getUrlImagen());
        producto.setPublicId(dto.getPublicId());
        producto.setEstado(dto.getEstado());
    }

    private ProductoDTO convertirADTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setCodigoProducto(producto.getCodigoProducto());
        dto.setNombreProducto(producto.getNombreProducto());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecioCompra(producto.getPrecioCompra());
        dto.setPrecioVenta(producto.getPrecioVenta());
        dto.setStockActual(producto.getStockActual());
        dto.setStockMinimo(producto.getStockMinimo());
        dto.setStockMaximo(producto.getStockMaximo());
        dto.setUrlImagen(producto.getUrlImagen());
        dto.setPublicId(producto.getPublicId());
        dto.setEstado(producto.getEstado());
        dto.setFechaCreacion(producto.getFechaCreacion());
        dto.setFechaModificacion(producto.getFechaModificacion());

        if (producto.getCategoria() != null) {
            dto.setIdCategoria(producto.getCategoria().getIdCategoria());
            dto.setNombreCategoria(producto.getCategoria().getNombreCategoria());
        }
        if (producto.getCreatedBy() != null) {
            dto.setCreatedById(producto.getCreatedBy().getIdUsuario());
            dto.setCreatedByNombreUsuario(producto.getCreatedBy().getNombreUsuario());
        }
        if (producto.getUpdatedBy() != null) {
            dto.setUpdatedById(producto.getUpdatedBy().getIdUsuario());
            dto.setUpdatedByNombreUsuario(producto.getUpdatedBy().getNombreUsuario());
        }
        return dto;
    }
}