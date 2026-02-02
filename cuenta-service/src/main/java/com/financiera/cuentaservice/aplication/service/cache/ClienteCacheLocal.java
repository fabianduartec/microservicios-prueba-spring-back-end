package com.financiera.cuentaservice.aplication.service.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ClienteCacheLocal {
    private final Map<Long, Boolean> clientesExistentes = new ConcurrentHashMap<>();

    public void marcarClienteExistente(Long idCliente) {
        clientesExistentes.put(idCliente, true);
        log.debug("idCliente {} cacheado", idCliente);
    }

    public boolean clienteExiste(Long idCliente) {
        return Boolean.TRUE.equals(clientesExistentes.get(idCliente));
    }
}

