package com.api.diversity.mappers;

import org.springframework.stereotype.Component;
import com.api.diversity.dtos.ClienteDto;
import com.api.diversity.model.Cliente;

@Component
public class ClienteMapper {

    public ClienteDto toDto(Cliente cliente) {
        ClienteDto dto = new ClienteDto();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setNombreCliente(cliente.getNombreCliente());
        dto.setTipoDocumento(cliente.getTipoDocumento() != null ? cliente.getTipoDocumento().name() : null);
        dto.setNumeroDocumento(cliente.getNumeroDocumento());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        dto.setEmail(cliente.getEmail());
        dto.setEstado(cliente.getEstado());
        dto.setFechaCreacion(cliente.getFechaCreacion());
        dto.setFechaModificacion(cliente.getFechaModificacion());
        return dto;
    }
}