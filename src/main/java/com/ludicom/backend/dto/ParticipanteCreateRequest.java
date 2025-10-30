package com.ludicom.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for Participante creation requests.
 */
public class ParticipanteCreateRequest {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 1, max = 200, message = "Nome deve ter entre 1 e 200 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Size(max = 150, message = "Email deve ter no máximo 150 caracteres")
    private String email;

    @NotBlank(message = "Documento é obrigatório")
    @Size(max = 30, message = "Documento deve ter no máximo 30 caracteres")
    private String documento;

    @Size(max = 100, message = "ID da instituição deve ter no máximo 100 caracteres")
    private String idInstituicao;

    @Size(max = 15, message = "RA deve ter no máximo 15 caracteres")
    private String ra;

    // Constructors
    public ParticipanteCreateRequest() {}

    public ParticipanteCreateRequest(String nome, String email, String documento, String idInstituicao, String ra) {
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.idInstituicao = idInstituicao;
        this.ra = ra;
    }

    // Getters and Setters
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

    public String getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(String idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }
}
