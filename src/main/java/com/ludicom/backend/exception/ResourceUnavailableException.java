package com.ludicom.backend.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when trying to use a resource that is unavailable
 */
public class ResourceUnavailableException extends BaseException {
    
    public ResourceUnavailableException(String message) {
        super("Recurso indisponível");
        addErrorDetail("detalhes", message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
