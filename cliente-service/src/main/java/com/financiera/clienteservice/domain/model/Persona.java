package com.financiera.clienteservice.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_persona", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("PERSONA")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public abstract class Persona {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "nombre_persona", nullable = false, length = 100)
    private String nombrePersona;

    @Column(name = "genero_persona", length = 20)
    private String generoPersona;

    @Column(name = "edad_persona")
    private Integer edadPersona;

    @Column(name = "direccion_persona", length = 200)
    private String direccionPersona;

    @Column(name = "telefono_persona", length = 20)
    private String telefonoPersona;

    private LocalDateTime fechaCreacion;

}