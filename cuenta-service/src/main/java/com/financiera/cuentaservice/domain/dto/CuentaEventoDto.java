package com.financiera.cuentaservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaEventoDto {
    private Long cuentaId;
    private Long numeroCuenta;
    private Long clienteId;
    private String clienteNombre;
    private String tipoCuenta;
    private BigDecimal saldo;
    private String tipoEvento;
    private BigDecimal valor;
    private String mensaje;
}
