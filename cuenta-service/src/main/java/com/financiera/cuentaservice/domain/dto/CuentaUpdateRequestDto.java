package com.financiera.cuentaservice.domain.dto;

import jakarta.validation.constraints.NotNull;

public record CuentaUpdateRequestDto(
        @NotNull(message = "Tipo Cuenta obligatorio")
        String cuentaTipo,
        @NotNull(message = "Estado es obligatorio")
        Boolean cuentaEstado
) {
}
