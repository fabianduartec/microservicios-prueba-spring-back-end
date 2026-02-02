package com.financiera.cuentaservice.domain.common;

public class NumeroCuentaDuplicadoException extends DomainException{
    public NumeroCuentaDuplicadoException(String numeroCuenta) {
        super("NUMERO_CUENTA_DUPLICADO",
                String.format("Numero de cuenta ya existe: %s", numeroCuenta));
    }
}
