package com.financiera.cuentaservice.domain.common;

import java.math.BigDecimal;

public class SaldoInsuficienteException extends DomainException{
    public SaldoInsuficienteException(BigDecimal saldoDisponible, BigDecimal saldoSolicitado){
        super("SALDO:ISUFICIENTE",
                String.format("Saldo insuficiente. Disponible: %2f, Solicitado: %.2f",
                saldoDisponible, saldoSolicitado));
    }
}
