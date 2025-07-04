package com.api.diversity.service;

import com.api.diversity.model.Entrada;
import com.api.diversity.model.Proveedor;
import com.api.diversity.model.Usuario;
import com.api.diversity.repository.EntradaRepository;
import com.api.diversity.repository.ProveedorRepository;
import com.api.diversity.repository.UsuarioRepository;
import com.api.diversity.dtos.EntradaDto;
import com.api.diversity.mappers.EntradaMapper;
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
public class EntradaService {

    @Autowired
    private EntradaRepository entradaRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final EntradaMapper mapper;

    public List<EntradaDto> listarTodos() {
        List<Entrada> entradas = entradaRepository.findAll();
        return entradas.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EntradaDto> listarPorProveedor(Long idProveedor) {
        List<Entrada> entradas = entradaRepository.findByProveedorIdProveedor(idProveedor);
        return entradas.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EntradaDto> listarPorEstado(String estado) {
        Entrada.EstadoEntrada estadoEnum = Entrada.EstadoEntrada.valueOf(estado);
        List<Entrada> entradas = entradaRepository.findByEstado(estadoEnum);
        return entradas.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<EntradaDto> buscarPorId(Long id) {
        return entradaRepository.findById(id)
                .map(mapper::toDto);
    }

    @Transactional
    public EntradaDto registrar(Entrada entrada) {
        Proveedor proveedor = proveedorRepository.findById(entrada.getProveedor().getIdProveedor())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Proveedor no encontrado con ID: " + entrada.getProveedor().getIdProveedor()));

        Usuario usuario = usuarioRepository.findById(entrada.getUsuarioRegistro().getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuario no encontrado con ID: " + entrada.getUsuarioRegistro().getIdUsuario()));

        // Validar que no exista una entrada con el mismo número de factura para el
        // mismo proveedor
        if (entradaRepository.existsByNumeroFacturaAndProveedorIdProveedor(
                entrada.getNumeroFactura(), proveedor.getIdProveedor())) {
            throw new EntityExistsException("Ya existe una entrada con este número de factura para el proveedor");
        }

        entrada.setProveedor(proveedor);
        entrada.setUsuarioRegistro(usuario);
        entrada.setFechaEntrada(LocalDateTime.now());

        if (entrada.getEstado() == null) {
            entrada.setEstado(Entrada.EstadoEntrada.Pendiente);
        }

        return mapper.toDto(entradaRepository.save(entrada));
    }

    @Transactional
    public EntradaDto aprobar(Long id, Long idUsuarioAprobacion) {
        Entrada entrada = entradaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entrada no encontrada con ID: " + id));

        Usuario usuarioAprobacion = usuarioRepository.findById(idUsuarioAprobacion)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuario no encontrado con ID: " + idUsuarioAprobacion));

        if (entrada.getEstado() != Entrada.EstadoEntrada.Pendiente) {
            throw new IllegalStateException("Solo se pueden aprobar entradas pendientes");
        }

        entrada.setEstado(Entrada.EstadoEntrada.Completado);
        entrada.setUsuarioAprobacion(usuarioAprobacion);
        entrada.setFechaAprobacion(LocalDateTime.now());

        return mapper.toDto(entradaRepository.save(entrada));
    }

    @Transactional
    public EntradaDto anular(Long id) {
        Entrada entrada = entradaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entrada no encontrada con ID: " + id));

        if (entrada.getEstado() == Entrada.EstadoEntrada.Completado) {
            throw new IllegalStateException("No se puede anular una entrada completada");
        }

        entrada.setEstado(Entrada.EstadoEntrada.Anulado);

        return mapper.toDto(entradaRepository.save(entrada));
    }

    public EntradaDto editar(Long id, Entrada entradaDetails) {
        Entrada entrada = entradaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entrada no encontrada con ID: " + id));

        if (entrada.getEstado() != Entrada.EstadoEntrada.Pendiente) {
            throw new IllegalStateException("Solo se pueden editar entradas pendientes");
        }

        if (entradaDetails.getObservaciones() != null) {
            entrada.setObservaciones(entradaDetails.getObservaciones());
        }

        return mapper.toDto(entradaRepository.save(entrada));
    }

    public void eliminar(Long id) {
        Entrada entrada = entradaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entrada no encontrada con ID: " + id));

        if (entrada.getEstado() == Entrada.EstadoEntrada.Completado) {
            throw new IllegalStateException("No se puede eliminar una entrada completada");
        }

        entradaRepository.delete(entrada);
    }
}