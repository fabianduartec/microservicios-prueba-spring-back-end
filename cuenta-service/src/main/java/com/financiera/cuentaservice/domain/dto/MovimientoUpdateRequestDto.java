package com.financiera.cuentaservice.domain.dto;

public record MovimientoUpdateRequestDto(
        Long movimientoId,
        Boolean movimientoTipoEstado,
        String motivo
) {

}
