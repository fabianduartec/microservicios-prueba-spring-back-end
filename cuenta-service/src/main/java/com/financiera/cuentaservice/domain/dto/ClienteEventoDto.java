package com.financiera.cuentaservice.domain.dto;

import lombok.Data;

@Data
public class ClienteEventoDto {
    private Long clienteId;
    private String nombre;
    private Long identificacion;
    private Boolean estado;
    private String tipoEvento;
}

