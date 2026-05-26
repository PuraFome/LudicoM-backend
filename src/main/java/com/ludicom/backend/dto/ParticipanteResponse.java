package com.ludicom.backend.dto;

/**
 * Data Transfer Object for Participante responses.
 */
public class ParticipanteResponse {

    private String uid;
    private String nome;
    private String email;
    private String documento;
    private InstituicaoResponse instituicao;
    private String ra;

    // Constructors
    public ParticipanteResponse() {}

    public ParticipanteResponse(String uid, String nome, String email, String documento, InstituicaoResponse instituicao, String ra) {
        this.uid = uid;
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.instituicao = instituicao;
        this.ra = ra;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public InstituicaoResponse getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(InstituicaoResponse instituicao) {
        this.instituicao = instituicao;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }
}
