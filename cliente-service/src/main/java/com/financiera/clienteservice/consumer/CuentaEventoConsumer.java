package com.financiera.clienteservice.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CuentaEventoConsumer {

    @KafkaListener(topics = "cuenta-events", groupId = "cliente-group")
    public void consume(String message) {
        log.info("CUENTA EVENTO RECIBIDO: {}", message);

        try {
            if (message.contains("CUENTA_CREADA")) {
                String numeroCuenta = extraerNumeroCuenta(message);
                String clienteId = extraerClienteId(message);
                log.info("CUENTA_CREADA: Cuenta={} Cliente={}", numeroCuenta, clienteId);
            }
            else if (message.contains("MOVIMIENTO_REGISTRADO")) {
                String numeroCuenta = extraerNumeroCuenta(message);
                String valor = extraerValor(message);
                String saldo = extraerSaldo(message);
                log.info("MOVIMIENTO_REGISTRADO: Cuenta={} Valor={} Saldo={}", numeroCuenta, valor, saldo);
            }
            else if (message.contains("SALDO_INSUFICIENTE")) {
                String numeroCuenta = extraerNumeroCuenta(message);
                String mensajeError = extraerMensaje(message);
                log.error("SALDO_INSUFICIENTE: Cuenta={} Mensaje={}", numeroCuenta, mensajeError);
            }
        } catch (Exception e) {
            log.error("Error procesando evento cuenta: {}", e.getMessage());
        }
    }

    private String extraerNumeroCuenta(String message) {
        return message.replaceAll("\"numeroCuenta\":\"([^\"]+)\"", "$1");
    }

    private String extraerClienteId(String message) {
        return message.replaceAll("\"clienteId\":\"([^\"]+)\"", "$1");
    }

    private String extraerValor(String message) {
        return message.replaceAll("\"valor\":([\\d.-]+)", "$1");
    }

    private String extraerSaldo(String message) {
        return message.replaceAll("\"saldo\":([\\d.-]+)", "$1");
    }

    private String extraerMensaje(String message) {
        return message.replaceAll("\"mensaje\":\"([^\"]+)\"", "$1");
    }
}