package com.api.diversity.repository;

import com.api.diversity.model.Salida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalidaRepository extends JpaRepository<Salida, Long> {
    Salida findByNumeroDocumentoAndTipoDocumento(String numeroDocumento, String tipoDocumento);
}
