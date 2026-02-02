package com.financiera.cuentaservice.domain.common;

public class MovimientoNoEncontradoException extends DomainException{
    public MovimientoNoEncontradoException(Long id){
        super("MOVIMIENTO_NO_ENCONTRADO",
                String.format("Movimiento no encontrado con el ID: %d", id));

    }
}
