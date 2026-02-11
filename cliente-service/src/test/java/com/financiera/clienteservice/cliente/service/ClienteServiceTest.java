package com.financiera.clienteservice.cliente.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.financiera.clienteservice.domain.model.Cliente;
import com.financiera.clienteservice.domain.repository.ClienteRepository;
import com.financiera.clienteservice.domain.dto.ClienteRequestDto;
import com.financiera.clienteservice.domain.dto.ClienteResponseDto;
import com.financiera.clienteservice.application.service.ClienteService;
import com.financiera.clienteservice.producer.ClienteEventoProducer;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteEventoProducer eventoProducer;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void serviceNotNull() {
        assertThat(clienteService).isNotNull();
    }

    @Test
    void crearCliente_ValidDataReturnsCliente() {

        Cliente clienteMock = new Cliente();
        clienteMock.setNombrePersona("Jose Lema");
        clienteMock.setGeneroPersona("M");
        clienteMock.setEdadPersona(30);
        clienteMock.setIdCliente(1234567890L);
        clienteMock.setTelefonoPersona("098254785");
        clienteMock.setContrasenaCliente("1234");
        clienteMock.setEstadoCliente(true);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteMock);

        doNothing().when(eventoProducer).sendClienteCreadoEvent(any());

        ClienteRequestDto dto = new ClienteRequestDto(
                "Jose Lema",
                "M",
                30,
                1234567890L,
                "Otavalo sn",
                "098254785",
                "1234",
                true
        );

        ClienteResponseDto result = clienteService.createCliente(dto);
        assertThat(result).isNotNull();
        assertThat(result.clienteNombre()).isEqualTo("Jose Lema");
        assertThat(result.clienteId()).isEqualTo(1234567890L
        );
    }
}