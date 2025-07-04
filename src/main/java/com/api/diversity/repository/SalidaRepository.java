package com.api.diversity.repository;

import com.api.diversity.model.Salida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalidaRepository extends JpaRepository<Salida, Long> {
    List<Salida> findByClienteIdCliente(Long idCliente);

    List<Salida> findByEstado(Salida.EstadoSalida estado);

    List<Salida> findByUsuarioRegistroIdUsuario(Long idUsuario);

    List<Salida> findByTipoDocumento(Salida.TipoDocumento tipoDocumento);

    Optional<Salida> findByNumeroDocumentoAndTipoDocumento(String numeroDocumento, Salida.TipoDocumento tipoDocumento);

    boolean existsByNumeroDocumentoAndTipoDocumento(String numeroDocumento, Salida.TipoDocumento tipoDocumento);
}