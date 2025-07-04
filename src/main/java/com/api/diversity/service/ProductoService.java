package com.api.diversity.service;

import com.api.diversity.model.Producto;
import com.api.diversity.model.Categoria;
import com.api.diversity.model.Usuario;
import com.api.diversity.repository.ProductoRepository;
import com.api.diversity.repository.CategoriaRepository;
import com.api.diversity.repository.UsuarioRepository;
import com.api.diversity.dtos.ProductoDto;
import com.api.diversity.mappers.ProductoMapper;
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
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final ProductoMapper mapper;

    public List<ProductoDto> listarTodos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductoDto> listarPorCategoria(Long idCategoria) {
        List<Producto> productos = productoRepository.findByCategoriaIdCategoria(idCategoria);
        return productos.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductoDto> listarStockBajo() {
        List<Producto> productos = productoRepository.findByStockActualLessThanEqual(0);
        return productos.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ProductoDto> buscarPorId(Long id) {
        return productoRepository.findById(id)
                .map(mapper::toDto);
    }

    public Optional<ProductoDto> buscarPorCodigo(String codigo) {
        return productoRepository.findByCodigoProducto(codigo)
                .map(mapper::toDto);
    }

    public ProductoDto registrar(Producto producto) {
        Categoria categoria = categoriaRepository.findById(producto.getCategoria().getIdCategoria())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Categoría no encontrada con ID: " + producto.getCategoria().getIdCategoria()));

        Usuario usuario = usuarioRepository.findById(producto.getCreatedBy().getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuario no encontrado con ID: " + producto.getCreatedBy().getIdUsuario()));

        // Validar que no exista un producto con el mismo código
        if (productoRepository.existsByCodigoProducto(producto.getCodigoProducto())) {
            throw new EntityExistsException("Ya existe un producto con este código");
        }

        producto.setCategoria(categoria);
        producto.setCreatedBy(usuario);

        if (producto.getEstado() == null) {
            producto.setEstado("Activo");
        }

        if (producto.getStockActual() == null) {
            producto.setStockActual(0);
        }

        if (producto.getStockMinimo() == null) {
            producto.setStockMinimo(0);
        }

        if (producto.getStockMaximo() == null) {
            producto.setStockMaximo(0);
        }

        return mapper.toDto(productoRepository.save(producto));
    }

    public ProductoDto editar(Long id, Producto productoDetails) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id));

        if (productoDetails.getNombreProducto() != null) {
            producto.setNombreProducto(productoDetails.getNombreProducto());
        }
        if (productoDetails.getDescripcion() != null) {
            producto.setDescripcion(productoDetails.getDescripcion());
        }
        if (productoDetails.getPrecioCompra() != null) {
            producto.setPrecioCompra(productoDetails.getPrecioCompra());
        }
        if (productoDetails.getPrecioVenta() != null) {
            producto.setPrecioVenta(productoDetails.getPrecioVenta());
        }
        if (productoDetails.getStockMinimo() != null) {
            producto.setStockMinimo(productoDetails.getStockMinimo());
        }
        if (productoDetails.getStockMaximo() != null) {
            producto.setStockMaximo(productoDetails.getStockMaximo());
        }
        if (productoDetails.getUrlImagen() != null) {
            producto.setUrlImagen(productoDetails.getUrlImagen());
        }
        if (productoDetails.getPublicId() != null) {
            producto.setPublicId(productoDetails.getPublicId());
        }
        if (productoDetails.getEstado() != null) {
            producto.setEstado(productoDetails.getEstado());
        }

        if (productoDetails.getCategoria() != null && productoDetails.getCategoria().getIdCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(productoDetails.getCategoria().getIdCategoria())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Categoría no encontrada con ID: " + productoDetails.getCategoria().getIdCategoria()));
            producto.setCategoria(categoria);
        }

        if (productoDetails.getUpdatedBy() != null && productoDetails.getUpdatedBy().getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(productoDetails.getUpdatedBy().getIdUsuario())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Usuario no encontrado con ID: " + productoDetails.getUpdatedBy().getIdUsuario()));
            producto.setUpdatedBy(usuario);
        }

        return mapper.toDto(productoRepository.save(producto));
    }

    public void eliminar(Long id) {
        productoRepository.findById(id).ifPresent(producto -> {
            producto.setEstado("Eliminado");
            productoRepository.save(producto);
        });
    }
}