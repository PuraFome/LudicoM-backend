package com.ludicom.backend.dto;

import java.time.LocalDateTime;
/*
 * Data Transfer Object for Jogo responses.
 */
public class JogoResponse {
    private String uid;
    private String nome;
    private String nomeAlternativo;
    private Integer anoPublicacao;
    private Integer tempoDeJogo; // em minutos
    private Integer minimoJogadores;
    private Integer maximoJogadores;
    private String codigoDeBarras;
    private Boolean isDisponivel;
    private LocalDateTime criadoQuando;
    private LocalDateTime atualizadoQuando;

    // Constructors
    public JogoResponse() {}

    public JogoResponse(String uid, String nome, String nomeAlternativo, Integer anoPublicacao, Integer tempoDeJogo,
                        Integer minimoJogadores, Integer maximoJogadores, String codigoDeBarras, Boolean isDisponivel, LocalDateTime criadoQuando, LocalDateTime atualizadoQuando) {
        this.uid = uid;
        this.nome = nome;
        this.nomeAlternativo = nomeAlternativo;
        this.anoPublicacao = anoPublicacao;
        this.tempoDeJogo = tempoDeJogo;
        this.minimoJogadores = minimoJogadores;
        this.maximoJogadores = maximoJogadores;
        this.codigoDeBarras = codigoDeBarras;
        this.isDisponivel = isDisponivel;
        this.criadoQuando = criadoQuando;
        this.atualizadoQuando = atualizadoQuando;
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

    public String getNomeAlternativo() {
        return nomeAlternativo;
    }

    public void setNomeAlternativo(String nomeAlternativo) {
        this.nomeAlternativo = nomeAlternativo;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public Integer getTempoDeJogo() {
        return tempoDeJogo;
    }

    public void setTempoDeJogo(Integer tempoDeJogo) {
        this.tempoDeJogo = tempoDeJogo;
    }

    public Integer getMinimoJogadores() {
        return minimoJogadores;
    }

    public void setMinimoJogadores(Integer minimoJogadores) {
        this.minimoJogadores = minimoJogadores;
    }

    public Integer getMaximoJogadores() {
        return maximoJogadores;
    }

    public void setMaximoJogadores(Integer maximoJogadores) {
        this.maximoJogadores = maximoJogadores;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }
    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public Boolean getIsDisponivel() {
        return isDisponivel;
    }
    public void setIsDisponivel(Boolean isDisponivel) {
        this.isDisponivel = isDisponivel;
    }

    public LocalDateTime getCriadoQuando() {
        return criadoQuando;
    }
    public void setCriadoQuando(LocalDateTime criadoQuando) {
        this.criadoQuando = criadoQuando;
    }

    public LocalDateTime getAtualizadoQuando() {
        return atualizadoQuando;
    }
    public void setAtualizadoQuando(LocalDateTime atualizadoQuando) {
        this.atualizadoQuando = atualizadoQuando;
    }

}