package com.ludicom.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "instituicao")
public class Instituicao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "text")
    private String uid;

    @Column(nullable = false, columnDefinition = "varchar(200)")
    private String nome;

    private String endereco;

    // Construtores
    public Instituicao() {
    }

    public Instituicao(String nome) {
        this.nome = nome;
    }

    public Instituicao(String nome, String endereco) {
        this(nome);
        this.endereco = endereco;
    }

    // Getters e Setters
    public String getUid() {
        return uid;
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