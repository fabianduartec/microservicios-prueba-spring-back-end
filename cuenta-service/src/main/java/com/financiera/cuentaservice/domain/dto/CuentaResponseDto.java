package com.financiera.cuentaservice.domain.dto;

import com.financiera.cuentaservice.domain.model.Movimiento;

import java.math.BigDecimal;
import java.util.List;

public record CuentaResponseDto(
        Long cuentaId,
        Long cuentaNumero,
        String cuentaTipo,
        BigDecimal cuentaSaldoInicial,
        BigDecimal cuentaSaldoActual,
        Boolean cuentaEstado,
        Long clienteId,
        String clienteNombre,
        Long totalMovimientos
) {
}
