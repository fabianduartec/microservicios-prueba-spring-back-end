package com.financiera.cuentaservice.domain.dto;

import com.financiera.cuentaservice.domain.common.TipoMovimiento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoResponseDto(
        Long movimientoId,
        LocalDateTime movimientoFecha,
        TipoMovimiento movimientoTipo,
        BigDecimal movimientoValor,
        BigDecimal movimientoSaldo,
        Boolean estadoMovimiento,
        Long cuentaId,
        String cuentaTipo
) {
}
