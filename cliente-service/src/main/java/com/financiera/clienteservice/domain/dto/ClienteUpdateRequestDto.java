package com.financiera.clienteservice.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteUpdateRequestDto(
        @NotBlank(message = "Nombre es obligatorio") @Size(max = 100) String clienteNombre,
        String clienteGenero,
        Integer clienteEdad,
        String clienteDireccion,
        String clienteTelefono,
        Boolean clienteEstado
) {
}
