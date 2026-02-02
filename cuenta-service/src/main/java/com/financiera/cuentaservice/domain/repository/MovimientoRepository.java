package com.financiera.cuentaservice.domain.repository;

import com.financiera.cuentaservice.domain.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
}
