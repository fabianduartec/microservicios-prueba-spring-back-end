package com.financiera.clienteservice.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("CLIENTE")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Cliente extends Persona {

    @Column(name = "id_cliente", nullable = false, unique = true)
    private Long idCliente;

    @Column(name = "contrasena_cliente", nullable = false, length = 100)
    private String contrasenaCliente;

    @Column(name = "estado_cliente", nullable = false)
    private Boolean estadoCliente = true;

}