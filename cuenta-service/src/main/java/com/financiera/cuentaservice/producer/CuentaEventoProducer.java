package com.financiera.cuentaservice.producer;

import com.financiera.cuentaservice.domain.dto.CuentaEventoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CuentaEventoProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "cuenta-events";

    public void sendCuentaCreadaEvent(CuentaEventoDto event) {
        kafkaTemplate.send(TOPIC, event.getCuentaId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("CUENTA_CREADA enviado: cuentaId={}", event.getCuentaId());
                    } else {
                        log.error("Error Kafka Producer: {}", ex.getMessage());
                    }
                });
    }

    public void sendMovimientoEvent(CuentaEventoDto event) {
        kafkaTemplate.send(TOPIC, event.getCuentaId().toString(), event)//igual que cuenta evento?
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Evento enviado: topic=cuenta-events, cuentaId={}",
                                event.getCuentaId());
                    } else {
                        log.error("Error Kafka Producer: {}", ex.getMessage());
                    }
                });
    }
}
