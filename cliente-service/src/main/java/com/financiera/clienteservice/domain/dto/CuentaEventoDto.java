package com.financiera.clienteservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaEventoDto {
    private Long cuentaId;
    private String numeroCuenta;
    private Long clienteId;
    private String tipoCuenta;
    private Double saldo;
    private String tipoEvento;
    private Double valor;
    private String mensaje;
}
