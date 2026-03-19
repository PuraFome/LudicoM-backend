package com.ludicom.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for Instituicao creation requests.
 */
public class InstituicaoCreateRequest {

    @NotBlank(message = "Nome da instituição é obrigatório")
    @Size(min = 1, max = 200, message = "Nome da instituição deve ter entre 1 e 200 caracteres")
    private String nome;

    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
    private String endereco;

    // Constructors
    public InstituicaoCreateRequest() {}

    public InstituicaoCreateRequest(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
    }

    // Getters and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
