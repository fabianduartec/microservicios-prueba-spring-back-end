package com.financiera.cuentaservice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClienteEventoConsumer {

    private final RestTemplate restTemplate;

    private final String CLIENTE_SERVICE_URL = "http://localhost:8080/clientes";

    public void validarCliente(Long clienteId) {
        try {
            String url = CLIENTE_SERVICE_URL + "/" + clienteId;
            log.info("🔍 VALIDANDO CLIENTE VIA REST → {}", url);

            // Llama GET al MS Cliente
            String response = restTemplate.getForObject(url, String.class);

            if (response == null || response.contains("404") || response.contains("error")) {
                throw new IllegalArgumentException("CLIENTE NO ENCONTRADO: " + clienteId);
            }

            log.info("CLIENTE VALIDADO VIA REST: {}", clienteId);
        } catch (Exception e) {
            log.error("ERROR VALIDANDO CLIENTE {}: {}", clienteId, e.getMessage());
            throw new IllegalArgumentException("CLIENTE NO DISPONIBLE: " + clienteId);
        }
    }
}