package com.ludicom.backend.config;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ludicom.backend.dto.ErrorResponse;
import com.ludicom.backend.exception.BaseException;

/**
 * Global exception handler for the application
 * Provides centralized error handling and consistent error responses
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse(
                "Erro de validação",
                errors,
                HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handle all custom exceptions that implement BaseException
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                ex.getErrorDetails(),
                ex.getHttpStatus().value());

        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    /**
     * Mapeia violações de integridade (FK, unique, etc.) para 409 (CONFLICT)
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Recurso em uso ou violação de integridade",
                HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    /**
     * Handle errors de parse de JSON, como formato inválido de data/hora
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotReadableException(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof DateTimeParseException) {
            Map<String, String> errors = new HashMap<>();
            errors.put("dataHora", "Formato de data ou hora inválido");
            ErrorResponse errorResponse = new ErrorResponse(
                    "Erro de validação",
                    errors,
                    HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        // Se não for erro de data/hora, retorna erro padrão
        ErrorResponse errorResponse = new ErrorResponse(
                "Ocorreu um erro inesperado",
                HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handle general exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        // Ignora ClientAbortException - ocorre quando o cliente fecha a conexão prematuramente
        // Não há nada que possamos fazer, pois o cliente já se desconectou
        if (ex.getClass().getName().contains("ClientAbortException")) {
            return null; // Retorna null para não tentar enviar resposta
        }
        
        // Log the exception for debugging
        System.err.println("Erro não tratado: " + ex.getClass().getName());
        System.err.println("Mensagem: " + ex.getMessage());

        ErrorResponse errorResponse;
        // Tratamento específico para erro de formato de data/hora
        if (ex instanceof DateTimeParseException) {
            Map<String, String> errors = new HashMap<>();
            errors.put("dataHora", "Formato de data ou hora inválido");
            errorResponse = new ErrorResponse(
                    "Erro de validação",
                    errors,
                    HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        errorResponse = new ErrorResponse(
                "Ocorreu um erro inesperado",
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
