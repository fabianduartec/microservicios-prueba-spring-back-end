package com.financiera.cuentaservice.domain.dto;
import java.time.LocalDateTime;
import java.util.List;

public record EstadoCuentaReporteDTO(
        List<CuentaReporteDTO> cuentas,
        List<MovimientoReporteDTO> movimientos
) {}