package com.financiera.cuentaservice.infrastructure.controller;

import com.financiera.cuentaservice.aplication.service.ReporteService;
import com.financiera.cuentaservice.domain.dto.EstadoCuentaReporteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/cuentas/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/generar")
    public ResponseEntity<EstadoCuentaReporteDTO> generarReporte(
            @RequestParam("cliente") Long idCliente,
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {

        EstadoCuentaReporteDTO reporte = reporteService.generarEstadoCuenta(idCliente, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
}