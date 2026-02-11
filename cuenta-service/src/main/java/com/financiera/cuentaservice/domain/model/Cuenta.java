package com.financiera.cuentaservice.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cuentas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cuenta")
    private Long idCuenta;

    @Column(name = "numero_cuenta", unique = true,nullable = false)
    private Long numeroCuenta;

    @Column(name="tipo_cuenta", nullable = false, length = 20)
    private String tipoCuenta;

    @Column(name = "saldo_inicial", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldoInicial = BigDecimal.ZERO;

    @Column(name = "saldo_actual", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldoActual = BigDecimal.ZERO;

    @Column(name="estado_cuenta", nullable = false)
    private Boolean estadoCuenta = true;
    @Column(name="id_cliente", nullable = false)
    private Long idCliente;

    @Column(length = 100)
    private String nombreCliente;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Movimiento> movimientos = new ArrayList<>();

}
