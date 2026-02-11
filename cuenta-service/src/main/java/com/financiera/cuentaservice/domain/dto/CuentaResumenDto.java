package com.financiera.cuentaservice.domain.dto;

import java.math.BigDecimal;

public record CuentaResumenDto (
        Long idCuenta,
        Long numeroCuenta,
        String tipoCuenta,
        BigDecimal saldoInicial,
        BigDecimal saldoActual,
        Boolean estadoCuenta,
        String nombreCliente
){
}
