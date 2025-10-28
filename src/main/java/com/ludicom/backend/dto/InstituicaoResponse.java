package com.ludicom.backend.dto;

/**
 * Data Transfer Object for Instituicao responses.
 */
public class InstituicaoResponse {

    private String uid;
    private String nome;
    private String endereco;

    // Constructors
    public InstituicaoResponse() {}

    public InstituicaoResponse(String uid, String nome, String endereco) {
        this.uid = uid;
        this.nome = nome;
        this.endereco = endereco;
    }

    // Getters and Setters
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

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
