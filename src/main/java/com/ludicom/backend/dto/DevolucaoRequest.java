package com.ludicom.backend.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for devolução (return) requests.
 */
public class DevolucaoRequest {

    @NotBlank(message = "Código de barras é obrigatório")
    private String codigoDeBarras;

    // Constructors
    public DevolucaoRequest() {
    }

    public DevolucaoRequest(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    // Getters and Setters
    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }
}
