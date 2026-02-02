package com.financiera.cuentaservice.domain.model;

import com.financiera.cuentaservice.domain.common.TipoEstadoMovimiento;
import com.financiera.cuentaservice.domain.common.TipoMovimiento;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "movimientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_movimiento")
    private Long idMovimiento;

    @Column(name = "fecha_movimiento", nullable = false)
    private LocalDateTime fechaMovimiento;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_movimiento", nullable = false)
    private TipoMovimiento tipoMovimiento;

    @DecimalMin("-999999.99") @DecimalMax("999999.99")
    @Column(name = "valor", precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(name = "saldo")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal saldo;

    @Enumerated(EnumType.STRING)
    @Column(name="estado_movimiento", nullable = false)
    private TipoEstadoMovimiento estadoMovimiento;

    @Column(name="id_cliente")
    private Long idCliente;

    @Size(max=100)
    private String nombreCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;

}
