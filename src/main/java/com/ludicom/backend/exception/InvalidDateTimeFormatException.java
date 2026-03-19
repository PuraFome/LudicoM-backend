package com.ludicom.backend.exception;

import org.springframework.http.HttpStatus;

/**
 * Exceção para erros de formato de data/hora em payloads
 */
public class InvalidDateTimeFormatException extends BaseException {

    public InvalidDateTimeFormatException(String fieldName, String message) {
        super("Erro de validação");
        addErrorDetail(fieldName, message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
