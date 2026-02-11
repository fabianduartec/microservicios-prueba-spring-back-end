package com.financiera.cuentaservice.domain.dto;

import com.financiera.cuentaservice.domain.common.TipoMovimiento;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MovimientoRequestDto(
        @NotNull(message = "Numero de cuenta es requerido") Long cuentaNumero,
        @DecimalMin(value = "-999999.99", message = "Valor muy bajo")
        @DecimalMax(value = "999999.99", message = "Valor muy alto")
        BigDecimal movimientoValor,
        TipoMovimiento movimientoTipo,
        Boolean tipoEstadoMovimiento
) {
}
