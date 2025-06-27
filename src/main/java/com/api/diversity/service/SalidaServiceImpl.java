package com.api.diversity.service;

import com.api.diversity.dto.SalidaDTO;
import com.api.diversity.mapper.SalidaMapper;
import com.api.diversity.model.Salida;
import com.api.diversity.model.Usuario;
import com.api.diversity.repository.SalidaRepository;
import com.api.diversity.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalidaServiceImpl implements SalidaService {

    @Autowired
    private SalidaRepository salidaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<SalidaDTO> findAll() {
        return salidaRepository.findAll()
                .stream()
                .map(SalidaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Salida findById(Long id) {
        return salidaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Salida no encontrada"));
    }

    @Override
    public SalidaDTO save(SalidaDTO salidaDTO) {
        if (salidaDTO.getIdSalida() != null) {
            // Actualización: buscar la entidad existente y actualizar campos
            Salida entity = salidaRepository.findById(salidaDTO.getIdSalida())
                .orElseThrow(() -> new IllegalArgumentException("Salida no encontrada para actualizar."));
            entity.setNumeroDocumento(salidaDTO.getNumeroDocumento());
            entity.setTipoDocumento(salidaDTO.getTipoDocumento());
            entity.setFechaSalida(salidaDTO.getFechaSalida());
            entity.setMotivoSalida(salidaDTO.getMotivoSalida());
            entity.setTotalVenta(salidaDTO.getTotalVenta());
            entity.setEstado(salidaDTO.getEstado());
            entity.setObservaciones(salidaDTO.getObservaciones());
            entity.setFechaAprobacion(salidaDTO.getFechaAprobacion());
            if (salidaDTO.getIdUsuarioRegistro() != null) {
                Usuario usuarioRegistro = usuarioRepository.findById(salidaDTO.getIdUsuarioRegistro()).orElse(null);
                entity.setUsuarioRegistro(usuarioRegistro);
            }
            if (salidaDTO.getIdUsuarioAprobacion() != null) {
                Usuario usuarioAprobacion = usuarioRepository.findById(salidaDTO.getIdUsuarioAprobacion()).orElse(null);
                entity.setUsuarioAprobacion(usuarioAprobacion);
            } else {
                entity.setUsuarioAprobacion(null);
            }
            Salida saved = salidaRepository.save(entity);
            return SalidaMapper.toDTO(saved);
        } else {
            // Creación: validar duplicados
            Salida existente = salidaRepository.findByNumeroDocumentoAndTipoDocumento(
                salidaDTO.getNumeroDocumento(), salidaDTO.getTipoDocumento());
            if (existente != null) {
                throw new IllegalArgumentException("Ya existe una salida con ese número y tipo de documento.");
            }
            Salida entity = SalidaMapper.toEntity(salidaDTO);
            if (salidaDTO.getIdUsuarioRegistro() != null) {
                Usuario usuarioRegistro = usuarioRepository.findById(salidaDTO.getIdUsuarioRegistro()).orElse(null);
                entity.setUsuarioRegistro(usuarioRegistro);
            }
            if (salidaDTO.getIdUsuarioAprobacion() != null) {
                Usuario usuarioAprobacion = usuarioRepository.findById(salidaDTO.getIdUsuarioAprobacion()).orElse(null);
                entity.setUsuarioAprobacion(usuarioAprobacion);
            }
            Salida saved = salidaRepository.save(entity);
            return SalidaMapper.toDTO(saved);
        }
    }

    @Override
    public void deleteById(Long id) {
        salidaRepository.deleteById(id);
    }
}
