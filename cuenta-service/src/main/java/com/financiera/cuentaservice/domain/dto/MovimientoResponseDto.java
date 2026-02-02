package com.financiera.cuentaservice.domain.dto;

import com.financiera.cuentaservice.domain.common.TipoEstadoMovimiento;
import com.financiera.cuentaservice.domain.common.TipoMovimiento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoResponseDto(
        Long movimientoId,
        LocalDateTime movimientoFecha,
        TipoMovimiento movimientoTipo,
        BigDecimal movimientoValor,
        BigDecimal movimientoSaldo,
        TipoEstadoMovimiento estadoMovimiento,
        Long cuentaId,
        String cuentaTipo,
        String nombreCliente
) {
}
