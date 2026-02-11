package com.financiera.clienteservice.consumer;

import com.financiera.clienteservice.application.service.ClienteService;
import com.financiera.clienteservice.domain.dto.CuentaEventoDto;
import com.financiera.clienteservice.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CuentaEventoConsumer {
    private final ClienteService clienteService;
    private final ClienteRepository clienteRepository;

    @KafkaListener(topics = "cuenta-events", groupId = "cliente-group")
    public void handleCuentaCreada(CuentaEventoDto event) {
        Long clienteId = event.getClienteId();
        log.info("CUENTA_CREADA recibido: {} para cliente: {}",
                event.getNumeroCuenta(), event.getClienteId());
        try {
            if (clienteRepository.existsByIdCliente(clienteId)) {
                log.info("Cliente {} YA EXISTE - NO crear automático", clienteId);
                return;
            }
            log.info("Creando cliente AUTOMÁTICO para cuenta: {}", event.getNumeroCuenta());
            clienteService.crearClienteAutomatico(event.getClienteId(), "Cliente " + event.getClienteNombre());

        } catch (Exception e) {
            log.error("Error procesando cuenta {}: {}", event.getNumeroCuenta(), e.getMessage());
        }
    }
}
