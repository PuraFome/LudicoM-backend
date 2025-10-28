package com.ludicom.backend.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a requested resource is not found
 */
public class ResourceNotFoundException extends BaseException {
    
    public ResourceNotFoundException(String message) {
        super("Recurso não encontrado");
        addErrorDetail("detalhes", message);
    }
    
    public ResourceNotFoundException(String resource, String field, String value) {
        super("Recurso não encontrado");
        addErrorDetail("detalhes", String.format("%s não encontrado com %s: %s", resource, field, value));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
