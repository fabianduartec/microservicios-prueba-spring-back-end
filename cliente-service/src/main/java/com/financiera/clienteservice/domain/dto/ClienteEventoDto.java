package com.financiera.clienteservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEventoDto {
    private Long clienteId;
    private String nombre;
    private Long identificacion;
    private Boolean estado;
    private String tipoEvento;
}
