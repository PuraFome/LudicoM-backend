package com.ludicom.backend.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {
    
    private final Map<String, String> errorDetails = new HashMap<>();
    
    public BaseException(String message) {
        super(message);
    }
    
    public Map<String, String> getErrorDetails() {
        return errorDetails;
    }
    
    protected void addErrorDetail(String key, String value) {
        errorDetails.put(key, value);
    }
    
    public abstract HttpStatus getHttpStatus();
}
