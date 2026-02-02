package com.financiera.clienteservice.domain.repository;

import com.financiera.clienteservice.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByIdCliente(Long idCliente);
    boolean existsByIdCliente(Long idCliente);
}
