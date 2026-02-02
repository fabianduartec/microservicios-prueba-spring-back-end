package com.financiera.cuentaservice.domain.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CuentaUpdateRequestDto(
        @NotNull(message = "Tipo Cuenta obligatorio")
        String cuentaTipo,
        @NotNull(message = "Estado es obligatorio")
        Boolean cuentaEstado
) {
}
