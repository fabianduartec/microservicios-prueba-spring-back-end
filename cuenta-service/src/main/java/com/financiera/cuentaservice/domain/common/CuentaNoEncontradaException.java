package com.financiera.cuentaservice.domain.common;

public class CuentaNoEncontradaException extends DomainException{
    public CuentaNoEncontradaException(Long numeroCuenta){
        super("CUENTA_NO_ENCONTRADA",
                String.format("Cuenta no encontrada: %s", numeroCuenta));
    }
}
