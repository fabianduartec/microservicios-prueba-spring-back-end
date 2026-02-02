package com.financiera.cuentaservice.infrastructure.controller;

import com.financiera.cuentaservice.aplication.service.CuentaService;
import com.financiera.cuentaservice.domain.dto.CuentaRequestDto;
import com.financiera.cuentaservice.domain.dto.CuentaResponseDto;
import com.financiera.cuentaservice.domain.dto.CuentaUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {
    private final CuentaService cuentaService;

    @PostMapping("/create/{idPersona}")
    public ResponseEntity<CuentaResponseDto> crearCuenta(@PathVariable Long idPersona, @Valid @RequestBody CuentaRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cuentaService.createCuenta(idPersona, request));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CuentaResponseDto>> listarCuentas() {
        return ResponseEntity.ok(cuentaService.getAllCuentas());
    }

    @GetMapping("/get/{idCuenta}")
    public ResponseEntity<CuentaResponseDto> obtenerCuenta(@PathVariable Long idCuenta) {
        return ResponseEntity.ok(cuentaService.getCuentaById(idCuenta));
    }

    @PatchMapping("/update/{idCuenta}")
    public ResponseEntity<CuentaResponseDto> actualizarCuenta(
            @PathVariable Long idCuenta,
            @Valid @RequestBody CuentaUpdateRequestDto request) {
        return ResponseEntity.ok(cuentaService.updateCuenta(idCuenta, request));
    }
}
