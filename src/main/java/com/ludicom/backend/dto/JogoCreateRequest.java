package com.ludicom.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for Jogo creation requests.
 */
public class JogoCreateRequest {

    @NotBlank(message = "Nome do jogo é obrigatório")
    @Size(min = 1, max = 200, message = "Nome do jogo deve ter entre 1 e 200 caracteres")
    private String nome;

    @Size(max = 200, message = "Nome alternativo deve ter no máximo 200 caracteres")
    private String nomeAlternativo;

    private Integer anoPublicacao;

    private Integer tempoDeJogo; // em minutos

    private Integer minimoJogadores;

    private Integer maximoJogadores;

    private String codigoDeBarras;

    private Boolean isDisponivel = true;

    // Constructors
    public JogoCreateRequest() {}

    public JogoCreateRequest(String nome, String nomeAlternativo, Integer anoPublicacao, Integer tempoDeJogo,
                             Integer minimoJogadores, Integer maximoJogadores, String codigoDeBarras, Boolean isDisponivel) {
        this.nome = nome;
        this.nomeAlternativo = nomeAlternativo;
        this.anoPublicacao = anoPublicacao;
        this.tempoDeJogo = tempoDeJogo;
        this.minimoJogadores = minimoJogadores;
        this.maximoJogadores = maximoJogadores;
        this.codigoDeBarras = codigoDeBarras;
        this.isDisponivel = isDisponivel;
    }

    // Getters and Setters
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

}