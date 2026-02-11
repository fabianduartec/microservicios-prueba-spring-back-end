package com.financiera.cuentaservice.infrastructure.controller;

import com.financiera.cuentaservice.aplication.service.MovimientoService;
import com.financiera.cuentaservice.domain.dto.MovimientoRequestDto;
import com.financiera.cuentaservice.domain.dto.MovimientoUpdateRequestDto;
import com.financiera.cuentaservice.domain.dto.MovimientoResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {
    private final MovimientoService movimientoService;

    @PostMapping("/create")
    public ResponseEntity<MovimientoResponseDto> crearMovimiento(@Valid @RequestBody MovimientoRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(movimientoService.registrarMovimientos(request));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MovimientoResponseDto>> listarMovimiento() {
        return ResponseEntity.ok(movimientoService.getAllMovimientos());
    }

    @GetMapping("/get/{idMovimiento}")
    public ResponseEntity<MovimientoResponseDto> obtenerMovimiento(@PathVariable Long idMovimiento) {
        return ResponseEntity.ok(movimientoService.getMovimientoById(idMovimiento));
    }

    @PutMapping("/update/{idMovimiento}")
    public ResponseEntity<MovimientoResponseDto> actualizarMovimiento(
            @PathVariable Long idCuenta,
            @Valid @RequestBody MovimientoUpdateRequestDto request) {
        return ResponseEntity.ok(movimientoService.updateMovimiento(idCuenta, request));
    }
}
