package com.financiera.cuentaservice.domain.common;

import java.math.BigDecimal;

public class SaldoInsuficienteException extends DomainException{
    public SaldoInsuficienteException(BigDecimal saldoDisponible, BigDecimal saldoSolicitado){
        super("SALDO:INSUFICIENTE",
                String.format("Saldo no Disponible. Saldo Actual: %2f, Valor Solicitado: %.2f",
                saldoDisponible, saldoSolicitado));
    }
}
