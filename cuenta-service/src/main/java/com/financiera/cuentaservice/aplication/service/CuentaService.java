package com.financiera.cuentaservice.aplication.service;

import com.financiera.cuentaservice.aplication.service.cache.ClienteCacheLocal;
import com.financiera.cuentaservice.domain.common.CuentaNoEncontradaException;
import com.financiera.cuentaservice.domain.dto.*;
import com.financiera.cuentaservice.domain.model.Cuenta;
import com.financiera.cuentaservice.domain.repository.CuentaRepository;
import com.financiera.cuentaservice.producer.CuentaEventoProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class CuentaService {
    private final CuentaRepository cuentaRepository;
    private final CuentaEventoProducer cuentaEventoProducer;
    private final ClienteCacheLocal clienteCacheLocal;

    public CuentaResponseDto createCuenta(Long idCliente, CuentaRequestDto request){
        if (!clienteCacheLocal.clienteExiste(idCliente)) {
            log.warn("idCliente {} propagándose via Kafka...", idCliente);
        }
        if (cuentaRepository.existsByNumeroCuenta(request.cuentaNumero())) {
            throw new IllegalArgumentException("Cuenta ya existe: " + request.cuentaNumero());
        }
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(request.cuentaNumero());
        cuenta.setTipoCuenta(request.cuentaTipo());
        cuenta.setSaldoInicial(request.cuentaSaldoInicial());
        cuenta.setSaldoActual(request.cuentaSaldoInicial());
        cuenta.setEstadoCuenta(true);
        cuenta.setIdCliente(idCliente);
        cuenta.setNombreCliente(request.clienteNombre());

        Cuenta saved = cuentaRepository.save(cuenta);

        CuentaEventoDto evento = CuentaEventoDto.builder()
                .cuentaId(cuenta.getIdCuenta())
                .numeroCuenta(cuenta.getNumeroCuenta())
                .clienteId(cuenta.getIdCliente())
                .tipoEvento("CUENTA_CREADA")
                .build();
        cuentaEventoProducer.sendCuentaCreadaEvent(evento);
        clienteCacheLocal.marcarClienteExistente(idCliente);
        return mapToResponse(saved);
    }
    public List<CuentaResponseDto> getAllCuentas(){
        List<Cuenta> listCuentas = cuentaRepository.findAll();
        return listCuentas.stream().map(this::mapToResponse).collect(Collectors.toList());
    }
    public CuentaResponseDto getCuentaById(Long idCuenta){
        Cuenta cuenta = cuentaRepository.findById(idCuenta).orElseThrow(()-> new CuentaNoEncontradaException(idCuenta));
        return mapToResponse(cuenta);
    }
    public CuentaResponseDto updateCuenta(Long idCuenta, CuentaUpdateRequestDto request){
        Cuenta cuenta = cuentaRepository.findById(idCuenta).orElseThrow(()-> new CuentaNoEncontradaException(idCuenta));
        cuenta.setTipoCuenta(request.cuentaTipo());
        cuenta.setEstadoCuenta(request.cuentaEstado());
        Cuenta cuentaUpdate = cuentaRepository.save(cuenta);
        return mapToResponse(cuentaUpdate);
    }
    private CuentaResponseDto mapToResponse(Cuenta cuenta){
        return new CuentaResponseDto(
                cuenta.getIdCuenta(),
                cuenta.getNumeroCuenta(),
                cuenta.getTipoCuenta(),
                cuenta.getSaldoInicial(),
                cuenta.getSaldoActual(),
                cuenta.getEstadoCuenta(),
                cuenta.getIdCliente(),
                cuenta.getNombreCliente(),
                (long) cuenta.getMovimientos().size()
        );
    }
}
