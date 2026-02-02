package com.financiera.cuentaservice.domain.repository;

import com.financiera.cuentaservice.domain.dto.MovimientoRequestDto;
import com.financiera.cuentaservice.domain.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByNumeroCuenta(Long numeroCuenta);
}
