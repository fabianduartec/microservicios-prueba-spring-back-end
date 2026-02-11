package com.financiera.cuentaservice.aplication.service;

import com.financiera.cuentaservice.domain.common.CuentaNoEncontradaException;
import com.financiera.cuentaservice.domain.common.SaldoInsuficienteException;
import com.financiera.cuentaservice.domain.common.TipoMovimiento;
import com.financiera.cuentaservice.domain.dto.CuentaEventoDto;
import com.financiera.cuentaservice.domain.dto.MovimientoRequestDto;
import com.financiera.cuentaservice.domain.dto.MovimientoResponseDto;
import com.financiera.cuentaservice.domain.dto.MovimientoUpdateRequestDto;
import com.financiera.cuentaservice.domain.model.Cuenta;
import com.financiera.cuentaservice.domain.model.Movimiento;
import com.financiera.cuentaservice.domain.repository.CuentaRepository;
import com.financiera.cuentaservice.domain.repository.MovimientoRepository;
import com.financiera.cuentaservice.producer.CuentaEventoProducer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class MovimientoService {
    public final MovimientoRepository movimientoRepository;
    public final CuentaRepository cuentaRepository;
    private final CuentaEventoProducer cuentaEventoProducer;

    public MovimientoResponseDto createMovimiento(Movimiento movimiento){
        Movimiento saved = movimientoRepository.save(movimiento);
        return mapToResponse(saved);
    }
    public List<MovimientoResponseDto> getAllMovimientos(){
        List<Movimiento> listMovimientos = movimientoRepository.findAll();
        return listMovimientos.stream().map(this::mapToResponse).collect(Collectors.toList());
    }
    public MovimientoResponseDto getMovimientoById(Long idMovimiento){
        Movimiento movimiento = movimientoRepository.findById(idMovimiento).orElseThrow(()-> new EntityNotFoundException("Movimiento no encontrado con Id: " + idMovimiento));
        return mapToResponse(movimiento);
    }
    public MovimientoResponseDto updateMovimiento(Long idCuenta, MovimientoUpdateRequestDto request){
        Movimiento movimiento = movimientoRepository.findById(request.movimientoId()).orElseThrow(()-> new EntityNotFoundException("Movimiento no encontrado con Id: " +request.movimientoId()));

        if (!movimiento.getCuenta().getNumeroCuenta().equals(idCuenta)) {
            throw new IllegalArgumentException("Movimiento no encontrado");
        }

        //Validacion estricta en Produccion
        validarEscenarioProduccion(movimiento.getEstadoMovimiento(), request.movimientoTipoEstado(), request.motivo());

        movimiento.setEstadoMovimiento(request.movimientoTipoEstado());
        Movimiento movimientoUpdate = movimientoRepository.save(movimiento);
        return mapToResponse(movimientoUpdate);
    }
    private MovimientoResponseDto mapToResponse(Movimiento movimiento){
        return new MovimientoResponseDto(
                movimiento.getIdMovimiento(),
                movimiento.getFechaMovimiento(),
                movimiento.getTipoMovimiento(),
                movimiento.getValor(),
                movimiento.getSaldo(),
                movimiento.getEstadoMovimiento(),
                movimiento.getCuenta().getNumeroCuenta(),
                movimiento.getCuenta().getTipoCuenta()
        );
    }

    public MovimientoResponseDto registrarMovimientos(MovimientoRequestDto movimientoRequestDto){
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimientoRequestDto.cuentaNumero()).orElseThrow(()-> new CuentaNoEncontradaException(movimientoRequestDto.cuentaNumero()));
        validarSaldoInsuficiente(cuenta, movimientoRequestDto);
        BigDecimal valorConSigno = obtenerValorConsigno(movimientoRequestDto);
        BigDecimal nuevoValor = calcularNuevoSaldo(cuenta.getSaldoActual(), valorConSigno);
        cuenta.setSaldoActual(nuevoValor);
        cuentaRepository.save(cuenta);

        Movimiento movimiento = Movimiento.builder()
                .fechaMovimiento(LocalDateTime.now())
                .tipoMovimiento(movimientoRequestDto.movimientoTipo())
                .valor(valorConSigno)
                .saldo(nuevoValor)
                .cuenta(cuenta)
                .estadoMovimiento(Boolean.TRUE)
                .build();
        MovimientoResponseDto movimientoResponse = createMovimiento(movimiento);
        CuentaEventoDto eventoExito = CuentaEventoDto.builder()
                .cuentaId(cuenta.getIdCuenta())
                .numeroCuenta(cuenta.getNumeroCuenta())
                .clienteId(cuenta.getIdCliente())
                .tipoEvento("MOVIMIENTO_REGISTRADO")
                .valor(valorConSigno)
                .saldo(nuevoValor)
                .build();

        cuentaEventoProducer.sendMovimientoEvent(eventoExito);
        log.info("Movimiento registrado→ cuenta#{} UPDATE=${} → movimiento#{} INSERT",
                cuenta.getNumeroCuenta(), nuevoValor, movimiento.getIdMovimiento());
        return movimientoResponse;
    }

    public void validarSaldoInsuficiente(Cuenta cuenta, MovimientoRequestDto request) {
        if (request.movimientoTipo() == TipoMovimiento.RETIRO) {
            if (request.movimientoValor().compareTo(BigDecimal.ZERO) > 0 && request.movimientoValor().compareTo(cuenta.getSaldoActual()) > 0) {
                CuentaEventoDto eventoError = CuentaEventoDto.builder()
                        .cuentaId(cuenta.getIdCuenta())
                        .numeroCuenta(cuenta.getNumeroCuenta())
                        .clienteId(cuenta.getIdCliente())
                        .tipoEvento("SALDO_INSUFICIENTE")
                        .mensaje("Saldo no disponible")
                        .valor(request.movimientoValor().negate())
                        .build();
                log.error("SALDO INSUFICIENTE → Cuenta={} SaldoActual={} Intentado={}",
                        cuenta.getNumeroCuenta(), cuenta.getSaldoActual(), request.movimientoValor());

                cuentaEventoProducer.sendMovimientoEvent(eventoError);
                throw new SaldoInsuficienteException(cuenta.getSaldoActual(),request.movimientoValor());
            }
        }
    }
    public BigDecimal calcularNuevoSaldo(BigDecimal saldoActual, BigDecimal valor){
        return saldoActual.add(valor);
    }
    public BigDecimal obtenerValorConsigno(MovimientoRequestDto request){
        return switch (request.movimientoTipo()){
            case DEPOSITO, REVERSA -> request.movimientoValor();
            case RETIRO -> request.movimientoValor().negate();
        };
    }
    public void validarEscenarioProduccion(Boolean actual, Boolean nuevo, String motivo){
        if (Boolean.FALSE.equals(actual)) {
            throw new UnsupportedOperationException(
                    "PRODUCCIÓN: Movimientos históricos CONFIRMADOS no se actualizan."
            );
        }
        validarEscenariosProvisional(nuevo, motivo);
    }

    private void validarEscenariosProvisional(Boolean nuevo, String motivo) {
        final Set<String> motivosConfirmacion = Set.of("cheque", "cobro");
        final Set<String> motivosCancelacion = Set.of("comision", "parcial", "error", "duplicado");
        final Set<String> motivosConciliacion = Set.of("conciliacion");

        if (nuevo != null) {
            if (nuevo && coincidenPalabras(motivo, motivosConfirmacion)) {
                return;
            }
            if (!nuevo && coincidenPalabras(motivo, motivosConciliacion)) {
                return;
            }
            if (nuevo && coincidenPalabras(motivo, motivosCancelacion)) {
                return;
            }
        }
        throw new UnsupportedOperationException(
                "PRODUCCIÓN: Solo 4 escenarios específicos permitidos para PROVISIONAL: " +
                        "cheque/cobro (→CONFIRMADO), conciliacion (→CONFIRMADO), " +
                        "comision/parcial/error/duplicado (→CANCELADO)"
        );
    }

    private boolean coincidenPalabras(String motivo, Set<String> palabrasClave) {
        return Optional.ofNullable(motivo)
                .map(String::toLowerCase)
                .filter(m -> palabrasClave.stream().anyMatch(m::contains))
                .isPresent();
    }
}
