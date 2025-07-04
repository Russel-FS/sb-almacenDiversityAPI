package com.api.diversity.controller;

import com.api.diversity.dtos.CategoriaDto;
import com.api.diversity.model.Categoria;
import com.api.diversity.service.CategoriaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listar")
    public List<CategoriaDto> listarTodasLasCategorias() {
        return categoriaService.listarTodos();
    }

    @GetMapping("/listar-por-rubro/{idRubro}")
    public List<CategoriaDto> listarCategoriasPorRubro(@PathVariable Long idRubro) {
        return categoriaService.listarPorRubro(idRubro);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarCategoria(@PathVariable Long id) {
        Optional<CategoriaDto> categoriaOptional = categoriaService.buscarPorId(id);
        if (categoriaOptional.isPresent()) {
            return ResponseEntity.ok(categoriaOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoría no encontrada con ID: " + id);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarCategoria(@RequestBody Categoria categoria) {
        try {
            CategoriaDto nuevaCategoria = categoriaService.registrar(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar categoría: " + e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarCategoria(@PathVariable Long id, @RequestBody Categoria categoriaDetails) {
        try {
            CategoriaDto categoriaActualizada = categoriaService.editar(id, categoriaDetails);
            return ResponseEntity.ok(categoriaActualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al editar categoría: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id) {
        try {
            categoriaService.eliminar(id);
            return ResponseEntity.ok().body("Categoría con ID " + id + " eliminada correctamente.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}