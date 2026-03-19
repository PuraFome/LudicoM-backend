package com.ludicom.backend.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception lançada quando se tenta deletar um recurso que ainda está em uso
 * por registros dependentes (ex: empréstimos ativos referenciando um jogo).
 */
public class ResourceInUseException extends BaseException {

    // Variante genérica (não expõe identificadores nem contagem)
    public ResourceInUseException(String resource) {
        super("Recurso em uso");
        addErrorDetail("detalhes", resource + " está em uso e não pode ser removido");
    }

    public ResourceInUseException(String resource, String field, String value) {
        super("Recurso em uso");
        addErrorDetail("detalhes", String.format("%s está em uso com %s: %s", resource, field, value));
    }

    public ResourceInUseException(String resource, String field, String value, long dependenciasAtivas) {
        super("Recurso em uso");
        addErrorDetail("detalhes", String.format("%s está em uso com %s: %s", resource, field, value));
        addErrorDetail("dependenciasAtivas", String.valueOf(dependenciasAtivas));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
