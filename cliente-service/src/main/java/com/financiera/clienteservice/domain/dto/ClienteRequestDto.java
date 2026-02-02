package com.financiera.clienteservice.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record ClienteRequestDto(
        @NotBlank(message = "Nombre es obligatorio") @Size(max = 100) String clienteNombre,
        String clienteGenero,
        Integer clienteEdad,
        @NotNull(message = "Identificacion es requerida")
        @Min(value = 1000000, message = "Identificacion debe tener al menos 7 dígitos")
        @Max(value = 999999999, message = "Identificacion no puede exceder 9 dígitos")
        Long clienteIdentificacion,
        String clienteDireccion,
        String clienteTelefono,
        @NotBlank(message = "Contrasena es requerida") @Size(min = 4) String clienteContrasena,
        Boolean clienteEstado
) {
}
