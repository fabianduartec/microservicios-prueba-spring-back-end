package com.financiera.clienteservice.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("CLIENTE")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Cliente extends Persona {

    @Column(name = "id_cliente", nullable = false, unique = true)
    private Long idCliente;

    @Column(name = "contrasena_cliente", nullable = false, length = 100)
    private String contrasenaCliente;

    @Column(name = "estado_cliente", nullable = false)
    private Boolean estadoCliente = true;

}