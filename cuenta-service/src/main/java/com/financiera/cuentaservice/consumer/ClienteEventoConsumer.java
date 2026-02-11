package com.financiera.cuentaservice.consumer;

import com.financiera.cuentaservice.aplication.service.cache.ClienteCacheLocal;
import com.financiera.cuentaservice.domain.dto.ClienteEventoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClienteEventoConsumer {
    private final ClienteCacheLocal clienteCacheLocal;

    @KafkaListener(topics = "cliente-events", groupId = "cuenta-group")
    public void handleClienteCreado(ClienteEventoDto event) {
        log.info("CLIENTE_CREADO recibido: ID={} Nombre={}",
                event.getClienteId(), event.getNombre());
        clienteCacheLocal.marcarClienteExistente(event.getClienteId());
        log.info("Cache actualizado para clienteId={}", event.getClienteId());
    }
}