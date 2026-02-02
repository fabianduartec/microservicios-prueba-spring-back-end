package com.financiera.clienteservice.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteUpdateRequestPasswordDto(
        @NotBlank(message = "Contrasena antigua es requerida") @Size(min = 4) String clienteContrasenaActual,
        @NotBlank(message = "Contrasena nueva es requerida") @Size(min = 4) String clienteContrasenaNueva
) {
}
