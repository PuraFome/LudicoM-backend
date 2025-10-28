package com.ludicom.backend.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when trying to create a resource that already exists
 */
public class ResourceAlreadyExistsException extends BaseException {
    
    public ResourceAlreadyExistsException(String message) {
        super("Recurso já existe");
        addErrorDetail("detalhes", message);
    }
    
    public ResourceAlreadyExistsException(String resource, String field, String value) {
        super("Recurso já existe");
        addErrorDetail("detalhes", String.format("%s já existe com %s: %s", resource, field, value));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
