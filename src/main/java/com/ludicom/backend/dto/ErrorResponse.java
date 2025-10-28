package com.ludicom.backend.dto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

/**
 * Standardized error response for all API errors
 */
public record ErrorResponse(
    String message,
    Map<String, String> errors,
    LocalDateTime timestamp,
    int status
) {
    public ErrorResponse(String message, int status) {
        this(message, Collections.emptyMap(), LocalDateTime.now(), status);
    }

    public ErrorResponse(String message, Map<String, String> errors, int status) {
        this(message, errors, LocalDateTime.now(), status);
    }
}
