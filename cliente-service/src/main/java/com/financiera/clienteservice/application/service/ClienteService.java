package com.financiera.clienteservice.application.service;

import com.financiera.clienteservice.domain.dto.*;
import com.financiera.clienteservice.domain.model.Cliente;
import com.financiera.clienteservice.domain.repository.ClienteRepository;
import com.financiera.clienteservice.producer.ClienteEventoProducer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ClienteEventoProducer clienteEventoProducer;


    public ClienteResponseDto createCliente(ClienteRequestDto request){
        Cliente cliente = new Cliente();
        cliente.setIdCliente(request.clienteIdentificacion());
        cliente.setNombrePersona(request.clienteNombre());
        cliente.setTelefonoPersona(request.clienteTelefono());
        cliente.setGeneroPersona(request.clienteGenero());
        cliente.setEdadPersona(request.clienteEdad());
        cliente.setDireccionPersona(request.clienteDireccion());
        cliente.setContrasenaCliente(new BCryptPasswordEncoder().encode(request.clienteContrasena()));
        cliente.setEstadoCliente(Boolean.TRUE);
        cliente.setFechaCreacion(LocalDateTime.now());

        Cliente saved = clienteRepository.save(cliente);

        ClienteEventoDto evento = ClienteEventoDto.builder()
                .clienteId(saved.getIdCliente())
                .nombre(saved.getNombrePersona())
                .identificacion(saved.getIdPersona())
                .estado(saved.getEstadoCliente())
                .tipoEvento("CLIENTE_CREADO")
                .build();

        clienteEventoProducer.sendClienteCreadoEvent(evento);
        log.info("CLIENTE_CREADO enviado a Kafka: ID={}, Nombre={}",
                saved.getIdCliente(), saved.getNombrePersona());

        return mapToResponse(saved);
    }
    public List<ClienteResponseDto> getAllClientes(){
        List<Cliente> listClientes = clienteRepository.findAll();
        return listClientes.stream().map(this::mapToResponse).collect(Collectors.toList());
    }
    public ClienteResponseDto getClienteById(Long idCliente){
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(()-> new EntityNotFoundException("Cliente no encontrado con Id: " + idCliente));
        return mapToResponse(cliente);
    }
    public ClienteResponseDto updateCliente(Long idPersona, ClienteUpdateRequestDto request){
        Cliente cliente = clienteRepository.findById(idPersona).orElseThrow(()-> new EntityNotFoundException("Cliente no encontrado con Id: " +idPersona));
        cliente.setNombrePersona(request.clienteNombre());
        cliente.setGeneroPersona(request.clienteGenero());
        cliente.setEdadPersona(request.clienteEdad());
        cliente.setDireccionPersona(request.clienteDireccion());
        cliente.setTelefonoPersona(request.clienteTelefono());
        cliente.setEstadoCliente(request.clienteEstado());
        Cliente clienteUpdate = clienteRepository.save(cliente);
        return mapToResponse(clienteUpdate);
    }
    public ClienteResponseDto updatePaswordCliente(Long idPersona, ClienteUpdateRequestPasswordDto request){
        Cliente cliente = clienteRepository.findById(idPersona).orElseThrow(()-> new EntityNotFoundException("Cliente no encontrado con Id: " +idPersona));
        if (!passwordEncoder.matches(request.clienteContrasenaActual(), cliente.getContrasenaCliente())) {
            throw new BadCredentialsException("Contrasena Actual incorrecta");
        }
        if (passwordEncoder.matches(request.clienteContrasenaActual(), request.clienteContrasenaNueva())) {
            throw new IllegalArgumentException("Nueva contrasena debe ser diferente");
        }
        cliente.setContrasenaCliente(new BCryptPasswordEncoder().encode(request.clienteContrasenaNueva()));
        Cliente clienteUpdate = clienteRepository.save(cliente);
        return mapToResponse(clienteUpdate);
    }
    public void deleteClientById(Long idCliente){
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(()-> new EntityNotFoundException("Cliente no encontrado con Id: " + idCliente));
        clienteRepository.delete(cliente);
    }
    private ClienteResponseDto mapToResponse(Cliente cliente){
        return new ClienteResponseDto(
                cliente.getIdPersona(),
                cliente.getIdCliente(),
                cliente.getNombrePersona(),
                cliente.getGeneroPersona(),
                cliente.getEdadPersona(),
                cliente.getDireccionPersona(),
                cliente.getTelefonoPersona(),
                cliente.getContrasenaCliente(),
                cliente.getEstadoCliente()
        );
    }
    public void crearClienteAutomatico(Long idCliente, String nombreCliente) {
        log.info("Cliente automático: idCliente={}", idCliente);

        if (clienteRepository.existsByIdCliente(idCliente)) {
            log.info("Cliente idCliente={} YA EXISTE", idCliente);
            return;
        }
        ClienteRequestDto request = ClienteRequestDto.builder()
                .clienteIdentificacion(idCliente)
                .clienteNombre(nombreCliente)
                .clienteEstado(Boolean.TRUE)
                .build();
        createCliente(request);
    }
}
