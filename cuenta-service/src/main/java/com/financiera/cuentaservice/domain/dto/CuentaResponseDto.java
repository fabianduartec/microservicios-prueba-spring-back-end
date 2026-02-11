package com.financiera.cuentaservice.domain.dto;

import java.math.BigDecimal;

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
