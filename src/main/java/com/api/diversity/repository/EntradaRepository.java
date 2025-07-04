package com.api.diversity.repository;

import com.api.diversity.model.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    List<Entrada> findByProveedorIdProveedor(Long idProveedor);

    List<Entrada> findByEstado(Entrada.EstadoEntrada estado);

    List<Entrada> findByUsuarioRegistroIdUsuario(Long idUsuario);

    Optional<Entrada> findByNumeroFacturaAndProveedorIdProveedor(String numeroFactura, Long idProveedor);

    boolean existsByNumeroFacturaAndProveedorIdProveedor(String numeroFactura, Long idProveedor);
}