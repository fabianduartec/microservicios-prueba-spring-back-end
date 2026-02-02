package com.financiera.cuentaservice.domain.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
@Slf4j
public class DomainExceptionHandler {

    public final MessageSource messageSource;

    public DomainExceptionHandler(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<ErrorResponse> handleSaldoInsuficiente(SaldoInsuficienteException ex) {
        log.error("Saldo insuficiente: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(ex.getCodigo(), ex.getMessage()));
    }

    @ExceptionHandler(CuentaNoEncontradaException.class)
    public ResponseEntity<ErrorResponse> handleCuentaNoEncontrada(CuentaNoEncontradaException ex) {
        log.error("Cuenta no encontrada: {}", ex.getCodigo());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getCodigo(), ex.getMessage(), 404));
    }

    @ExceptionHandler(MovimientoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleMovimientoNoEncontrada(MovimientoNoEncontradoException ex) {
        log.error("Movimiento no encontrado: {}", ex.getCodigo());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getCodigo(), ex.getMessage(), 404));
    }

    @ExceptionHandler(NumeroCuentaDuplicadoException.class)
    public ResponseEntity<ErrorResponse> handleNumeroCuentaDuplicado(NumeroCuentaDuplicadoException ex) {
        log.error("Numero cuenta duplicado: {}", ex.getCodigo());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getCodigo(), ex.getMessage(), 409));
    }

    @ExceptionHandler(ClienteNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleClienteNoEncontrado(ClienteNoEncontradoException ex) {
        log.error("Cliente no encontrado: {}", ex.getCodigo());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getCodigo(), ex.getMessage(), 404));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        log.warn("Validacion DTO Fallida");
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errores.put(field, message);
        });

        ErrorResponse error = new ErrorResponse(
                "VALIDACION_DTO",
                "Datos de entrada inválidos",
                errores,
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);  // Log completo

        // ← CAMBIA ESTO:
        ErrorResponse error = new ErrorResponse(
                "ERROR_INTERNO",
                ex.getMessage() + " - " + ex.getClass().getSimpleName(),  // Muestra error real
                500
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
