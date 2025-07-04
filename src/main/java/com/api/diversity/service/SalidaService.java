package com.api.diversity.service;

import com.api.diversity.model.Salida;
import com.api.diversity.model.Cliente;
import com.api.diversity.model.Usuario;
import com.api.diversity.repository.SalidaRepository;
import com.api.diversity.repository.ClienteRepository;
import com.api.diversity.repository.UsuarioRepository;
import com.api.diversity.dtos.SalidaDto;
import com.api.diversity.mappers.SalidaMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalidaService {

    @Autowired
    private SalidaRepository salidaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final SalidaMapper mapper;

    public List<SalidaDto> listarTodos() {
        List<Salida> salidas = salidaRepository.findAll();
        return salidas.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SalidaDto> listarPorCliente(Long idCliente) {
        List<Salida> salidas = salidaRepository.findByClienteIdCliente(idCliente);
        return salidas.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SalidaDto> listarPorEstado(String estado) {
        Salida.EstadoSalida estadoEnum = Salida.EstadoSalida.valueOf(estado);
        List<Salida> salidas = salidaRepository.findByEstado(estadoEnum);
        return salidas.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SalidaDto> listarPorTipoDocumento(String tipoDocumento) {
        Salida.TipoDocumento tipoEnum = Salida.TipoDocumento.valueOf(tipoDocumento);
        List<Salida> salidas = salidaRepository.findByTipoDocumento(tipoEnum);
        return salidas.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<SalidaDto> buscarPorId(Long id) {
        return salidaRepository.findById(id)
                .map(mapper::toDto);
    }

    @Transactional
    public SalidaDto registrar(Salida salida) {
        Usuario usuario = usuarioRepository.findById(salida.getUsuarioRegistro().getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuario no encontrado con ID: " + salida.getUsuarioRegistro().getIdUsuario()));

        // Validar cliente si se proporciona
        if (salida.getCliente() != null && salida.getCliente().getIdCliente() != null) {
            Cliente cliente = clienteRepository.findById(salida.getCliente().getIdCliente())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Cliente no encontrado con ID: " + salida.getCliente().getIdCliente()));
            salida.setCliente(cliente);
        }

        // Validar que no exista una salida con el mismo número de documento y tipo
        if (salidaRepository.existsByNumeroDocumentoAndTipoDocumento(
                salida.getNumeroDocumento(), salida.getTipoDocumento())) {
            throw new EntityExistsException("Ya existe una salida con este número de documento y tipo");
        }

        salida.setUsuarioRegistro(usuario);
        salida.setFechaSalida(LocalDateTime.now());

        if (salida.getEstado() == null) {
            salida.setEstado(Salida.EstadoSalida.Pendiente);
        }

        return mapper.toDto(salidaRepository.save(salida));
    }

    @Transactional
    public SalidaDto aprobar(Long id, Long idUsuarioAprobacion) {
        Salida salida = salidaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salida no encontrada con ID: " + id));

        Usuario usuarioAprobacion = usuarioRepository.findById(idUsuarioAprobacion)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuario no encontrado con ID: " + idUsuarioAprobacion));

        if (salida.getEstado() != Salida.EstadoSalida.Pendiente) {
            throw new IllegalStateException("Solo se pueden aprobar salidas pendientes");
        }

        salida.setEstado(Salida.EstadoSalida.Completado);
        salida.setUsuarioAprobacion(usuarioAprobacion);
        salida.setFechaAprobacion(LocalDateTime.now());

        return mapper.toDto(salidaRepository.save(salida));
    }

    @Transactional
    public SalidaDto anular(Long id) {
        Salida salida = salidaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salida no encontrada con ID: " + id));

        if (salida.getEstado() == Salida.EstadoSalida.Completado) {
            throw new IllegalStateException("No se puede anular una salida completada");
        }

        salida.setEstado(Salida.EstadoSalida.Anulado);

        return mapper.toDto(salidaRepository.save(salida));
    }

    public SalidaDto editar(Long id, Salida salidaDetails) {
        Salida salida = salidaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salida no encontrada con ID: " + id));

        if (salida.getEstado() != Salida.EstadoSalida.Pendiente) {
            throw new IllegalStateException("Solo se pueden editar salidas pendientes");
        }

        if (salidaDetails.getMotivoSalida() != null) {
            salida.setMotivoSalida(salidaDetails.getMotivoSalida());
        }
        if (salidaDetails.getObservaciones() != null) {
            salida.setObservaciones(salidaDetails.getObservaciones());
        }

        return mapper.toDto(salidaRepository.save(salida));
    }

    public void eliminar(Long id) {
        Salida salida = salidaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salida no encontrada con ID: " + id));

        if (salida.getEstado() == Salida.EstadoSalida.Completado) {
            throw new IllegalStateException("No se puede eliminar una salida completada");
        }

        salidaRepository.delete(salida);
    }
}