package com.financiera.cuentaservice.domain.dto;

import java.math.BigDecimal;

public record MovimientoReporteDTO(
        String fecha,
        Boolean estado,
        BigDecimal movimiento
) {}