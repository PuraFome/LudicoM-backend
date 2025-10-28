package com.ludicom.backend.exception;

import org.springframework.http.HttpStatus;

public class RequiredFieldException extends BaseException {
    
    public RequiredFieldException(String resourceName, String fieldName) {
        super("Campo obrigatório não informado");
        addErrorDetail("detalhes", String.format("O campo '%s' é obrigatório para %s", fieldName, resourceName));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
