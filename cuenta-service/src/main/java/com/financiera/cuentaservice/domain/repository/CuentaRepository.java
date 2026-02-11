package com.financiera.cuentaservice.domain.repository;

import com.financiera.cuentaservice.domain.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByNumeroCuenta(Long numeroCuenta);
    Boolean existsByNumeroCuenta(Long numeroCuenta);
    @Query("SELECT c FROM Cuenta c WHERE c.idCliente = :idCliente AND c.estadoCuenta = true")
    List<Cuenta> findByIdClienteAndEstadoCuentaTrue(@Param("idCliente") Long idCliente);

    @Query("SELECT COUNT(c) > 0 FROM Cuenta c WHERE c.idCliente = :idCliente AND c.estadoCuenta = true")
    boolean existsByIdClienteAndEstadoCuentaTrue(@Param("idCliente") Long idCliente);

}
