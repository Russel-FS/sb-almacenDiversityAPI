package com.api.diversity.controller;

import com.api.diversity.dtos.ProductoDto;
import com.api.diversity.model.Producto;
import com.api.diversity.service.ProductoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/listar")
    public List<ProductoDto> listarTodosLosProductos() {
        return productoService.listarTodos();
    }

    @GetMapping("/listar-por-categoria/{idCategoria}")
    public List<ProductoDto> listarProductosPorCategoria(@PathVariable Long idCategoria) {
        return productoService.listarPorCategoria(idCategoria);
    }

    @GetMapping("/listar-stock-bajo")
    public List<ProductoDto> listarProductosStockBajo() {
        return productoService.listarStockBajo();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarProducto(@PathVariable Long id) {
        Optional<ProductoDto> productoOptional = productoService.buscarPorId(id);
        if (productoOptional.isPresent()) {
            return ResponseEntity.ok(productoOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con ID: " + id);
    }

    @GetMapping("/buscar-por-codigo/{codigo}")
    public ResponseEntity<?> buscarProductoPorCodigo(@PathVariable String codigo) {
        Optional<ProductoDto> productoOptional = productoService.buscarPorCodigo(codigo);
        if (productoOptional.isPresent()) {
            return ResponseEntity.ok(productoOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con código: " + codigo);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarProducto(@RequestBody Producto producto) {
        try {
            ProductoDto nuevoProducto = productoService.registrar(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar producto: " + e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarProducto(@PathVariable Long id, @RequestBody Producto productoDetails) {
        try {
            ProductoDto productoActualizado = productoService.editar(id, productoDetails);
            return ResponseEntity.ok(productoActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al editar producto: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        try {
            productoService.eliminar(id);
            return ResponseEntity.ok().body("Producto con ID " + id + " eliminado correctamente.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}