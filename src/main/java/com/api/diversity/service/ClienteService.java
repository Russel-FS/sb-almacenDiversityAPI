package com.api.diversity.service;

import com.api.diversity.model.Cliente;
import com.api.diversity.repository.ClienteRepository;
import com.api.diversity.dtos.ClienteDto;
import com.api.diversity.mappers.ClienteMapper;
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
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    private final ClienteMapper mapper;

    public List<ClienteDto> listarTodos() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ClienteDto> buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .map(mapper::toDto);
    }

    public ClienteDto registrar(Cliente cliente) {
        // Validar que no exista un cliente con el mismo documento
        if (clienteRepository.existsByTipoDocumentoAndNumeroDocumento(
                cliente.getTipoDocumento().name(), cliente.getNumeroDocumento())) {
            throw new EntityExistsException("Ya existe un cliente con este documento");
        }

        // Validar que no exista un cliente con el mismo email (si se proporciona)
        if (cliente.getEmail() != null && !cliente.getEmail().trim().isEmpty() &&
                clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new EntityExistsException("Ya existe un cliente con este email");
        }

        if (cliente.getEstado() == null) {
            cliente.setEstado("Activo");
        }

        return mapper.toDto(clienteRepository.save(cliente));
    }

    public ClienteDto editar(Long id, Cliente clienteDetails) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID: " + id));

        if (clienteDetails.getNombreCliente() != null) {
            cliente.setNombreCliente(clienteDetails.getNombreCliente());
        }
        if (clienteDetails.getDireccion() != null) {
            cliente.setDireccion(clienteDetails.getDireccion());
        }
        if (clienteDetails.getTelefono() != null) {
            cliente.setTelefono(clienteDetails.getTelefono());
        }
        if (clienteDetails.getEmail() != null) {
            cliente.setEmail(clienteDetails.getEmail());
        }
        if (clienteDetails.getEstado() != null) {
            cliente.setEstado(clienteDetails.getEstado());
        }

        return mapper.toDto(clienteRepository.save(cliente));
    }

    public void eliminar(Long id) {
        clienteRepository.findById(id).ifPresent(cliente -> {
            cliente.setEstado("Inactivo");
            clienteRepository.save(cliente);
        });
    }
}