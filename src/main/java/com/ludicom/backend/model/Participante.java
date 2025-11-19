package com.ludicom.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Entidade Participante para representar os participantes do sistema
 */

@Entity
@Table(name = "participante")
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uid", updatable = false, nullable = false)
    private String uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid_instituicao", nullable = true)
    private Instituicao instituicao;

    @NotBlank(message = "Insira o Nome")
    @Column(unique = false, nullable = false, columnDefinition = "varchar(200)")
    private String nome;

    @Column(unique = true, nullable = false, columnDefinition = "varchar(150)")
    private String email;

    @NotBlank(message = "Insira o documento")
    @Column(unique = true, nullable = false, columnDefinition = "varchar(30)")
    private String documento;

    @Column(unique = true, nullable = true, columnDefinition = "varchar(15)")
    private String ra;

    // Constructors
    public Participante() {
    }

    public Participante(String nome, String email, Instituicao instituicao, String documento, String ra) {
        this();
        this.nome = nome;
        this.email = email;
        this.instituicao = instituicao;
        this.documento = documento;
        this.ra = ra;

    }

    public Participante(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getId() {
        return uid;
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

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

}