package com.financiera.clienteservice.domain.repository;

import com.financiera.clienteservice.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
