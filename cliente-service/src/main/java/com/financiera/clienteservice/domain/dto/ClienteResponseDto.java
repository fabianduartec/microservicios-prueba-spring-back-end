package com.financiera.clienteservice.domain.dto;

public record ClienteResponseDto(
        Long personaId,
        Long clienteId,
        String clienteNombre,
        String clienteGenero,
        Integer clienteEdad,
        String clienteDireccion,
        String clienteTelefono,
        String clienteContrasena,
        Boolean clienteEstado
) {}
