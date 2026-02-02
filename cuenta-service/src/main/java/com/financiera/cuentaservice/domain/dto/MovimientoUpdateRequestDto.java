package com.financiera.cuentaservice.domain.dto;

import com.financiera.cuentaservice.domain.common.TipoEstadoMovimiento;

public record MovimientoUpdateRequestDto(
        Long movimientoId,
        TipoEstadoMovimiento movimientoTipoEstado,
        String motivo
) {

}
