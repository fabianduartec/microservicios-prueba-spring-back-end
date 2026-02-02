package com.financiera.clienteservice.infrastructure.controller;

import com.financiera.clienteservice.application.service.ClienteService;
import com.financiera.clienteservice.domain.dto.ClienteRequestDto;
import com.financiera.clienteservice.domain.dto.ClienteResponseDto;
import com.financiera.clienteservice.domain.dto.ClienteUpdateRequestDto;
import com.financiera.clienteservice.domain.dto.ClienteUpdateRequestPasswordDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @PostMapping("/create")
    public ResponseEntity<ClienteResponseDto> crearCliente(@Valid @RequestBody ClienteRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.createCliente(request));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ClienteResponseDto>> listarClientes() {
        return ResponseEntity.ok(clienteService.getAllClientes());
    }

    @GetMapping("/get/{idCliente}")
    public ResponseEntity<ClienteResponseDto> obtenerCliente(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.getClienteById(id));
    }

    @PutMapping("/update/{idPersona}")
    public ResponseEntity<ClienteResponseDto> actualizarCliente(
            @PathVariable Long idPersona,
            @Valid @RequestBody ClienteUpdateRequestDto request) {
        return ResponseEntity.ok(clienteService.updateCliente(idPersona, request));
    }
    @PutMapping("/update/{idPersona}/password")
    public ResponseEntity<ClienteResponseDto> actualizarContrasenaCliente(
            @PathVariable Long idPersona,
            @Valid @RequestBody ClienteUpdateRequestPasswordDto request) {
        return ResponseEntity.ok(clienteService.updatePaswordCliente(idPersona, request));
    }
    @DeleteMapping("delete/{idPersona}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long idPersona) {
        clienteService.deleteClientById(idPersona);
        return ResponseEntity.noContent().build();
    }
}
