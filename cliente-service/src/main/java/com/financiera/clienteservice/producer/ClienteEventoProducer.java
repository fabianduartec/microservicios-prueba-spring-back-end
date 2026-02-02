package com.financiera.clienteservice.producer;

import com.financiera.clienteservice.domain.dto.ClienteEventoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteEventoProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendClienteCreadoEvent(ClienteEventoDto event) {
        kafkaTemplate.send("cliente-events", event.getClienteId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("CLIENTE_CREADO enviado: ID={}", event.getClienteId());
                    } else {
                        log.error(" Error CLIENTE_CREADO: {}", ex.getMessage());
                    }
                });
    }
}

