package com.financiera.cuentaservice.domain.repository;

import com.financiera.cuentaservice.domain.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    @Query(value = """
SELECT m.*, c.numero_cuenta, c.tipo_cuenta, c.saldo_inicial, 
       c.estado_cuenta, c.nombre_cliente
FROM movimientos m
INNER JOIN cuentas c ON m.cuenta_id = c.id_cuenta
WHERE c.id_cliente = :clienteId
AND m.fecha_movimiento BETWEEN :fechaInicio AND :fechaFin
ORDER BY m.fecha_movimiento ASC
""", nativeQuery = true)
    List<Object[]> findMovimientosByClienteIdAndFecha(
            @Param("clienteId") Long clienteId,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );
}