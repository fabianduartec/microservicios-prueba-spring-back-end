package com.financiera.cuentaservice.domain.dto;

import java.math.BigDecimal;

public record CuentaReporteDTO(
        String cliente,
        Long numeroCuenta,
        String tipoCuenta,
        BigDecimal saldoInicial,
        BigDecimal saldoActual

) {}