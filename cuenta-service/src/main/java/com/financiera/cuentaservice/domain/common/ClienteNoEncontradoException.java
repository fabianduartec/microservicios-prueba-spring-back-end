package com.financiera.cuentaservice.domain.common;

public class ClienteNoEncontradoException extends DomainException{
    public ClienteNoEncontradoException(Long id){
        super("CLIENTE_NO_ENCONTRADO",
                String.format("Cliente no encontrado con el ID: %d", id));
    }
}
