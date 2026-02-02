package com.financiera.cuentaservice.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record CuentaRequestDto(
        @NotNull(message = "Numero de cuenta obligatorio")
        @Min(value = 100000L, message = "Minimo 6 digitos")
        @Max(value = 9999999999L, message = "Maximo 10 digitos")
        Long cuentaNumero,
        @NotNull(message = "Tipo Cuenta obligatorio")
        String cuentaTipo,
        @DecimalMin(value = "0.0", message = "Saldo Inicial mayor que Cero")
        @Digits(integer = 10, fraction = 2)
        BigDecimal cuentaSaldoInicial,
        @NotNull(message = "Estado es obligatorio")
        Boolean cuentaEstado,
        @NotNull(message = "Nombre Cliente es Obligatorio")
        String clienteNombre
) {
}
