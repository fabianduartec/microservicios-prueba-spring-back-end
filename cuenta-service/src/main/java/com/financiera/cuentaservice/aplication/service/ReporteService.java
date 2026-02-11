package com.financiera.cuentaservice.aplication.service;

import com.financiera.cuentaservice.domain.dto.CuentaReporteDTO;
import com.financiera.cuentaservice.domain.dto.EstadoCuentaReporteDTO;
import com.financiera.cuentaservice.domain.dto.MovimientoReporteDTO;
import com.financiera.cuentaservice.domain.model.Cuenta;
import com.financiera.cuentaservice.domain.repository.CuentaRepository;
import com.financiera.cuentaservice.domain.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReporteService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    public EstadoCuentaReporteDTO generarEstadoCuenta(Long idCliente, LocalDateTime fechaInicio, LocalDateTime fechaFin) {

        if (!cuentaRepository.existsByIdClienteAndEstadoCuentaTrue(idCliente)) {
            throw new IllegalArgumentException("No hay cuentas activas para cliente: " + idCliente);
        }
        List<CuentaReporteDTO> cuentas = cuentaRepository.findByIdClienteAndEstadoCuentaTrue(idCliente)
                .stream()
                .map(this::mapToCuentaReporte)
                .collect(Collectors.toList());
        DateTimeFormatter fechaFormato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Object[]> movimientosRaw = movimientoRepository.findMovimientosByClienteIdAndFecha(
                        idCliente, fechaInicio, fechaFin);
        List<MovimientoReporteDTO> movimientos = movimientosRaw.stream()
                .map(row -> {
                    LocalDateTime fecha = (LocalDateTime) row[2];

                    return new MovimientoReporteDTO(
                            fecha.format(fechaFormato),
                            (Boolean) row[1],
                            (BigDecimal) row[5]
                    );
                })
                .collect(Collectors.toList());

        return new EstadoCuentaReporteDTO(
                cuentas,
                movimientos
        );
    }
    private CuentaReporteDTO mapToCuentaReporte(Cuenta cuenta) {
        return new CuentaReporteDTO(
                cuenta.getNombreCliente(),
                cuenta.getNumeroCuenta(),
                cuenta.getTipoCuenta(),
                cuenta.getSaldoInicial(),
                cuenta.getSaldoActual()
        );
    }
}