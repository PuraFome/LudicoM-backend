package com.ludicom.backend.model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "jogo")
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uid", updatable = false, nullable = false)
    private String uid;

    @NotBlank(message = "Nome do jogo é obrigatório")
    @Column(nullable = false, columnDefinition="varchar(200)")
    private String nome;

    @Column(name = "nome_alternativo", columnDefinition="varchar(200)")
    private String nomeAlternativo;

    @Column(name = "ano_publicacao", columnDefinition="date")
    private LocalDate anoPublicacao;

    @Column(name = "tempo_de_jogo", columnDefinition="int")
    private Integer tempoDeJogo; // em minutos

    @Column(name = "minimo_jogadores", columnDefinition="int")
    private Integer minimoJogadores;

    @Column(name = "maximo_jogadores", columnDefinition="int")
    private Integer maximoJogadores;

    @Column(name = "codigo_de_barras")
    private String codigoDeBarras;

    @Column(name = "is_disponivel")
    private Boolean isDisponivel = true;

    @Column(name = "criado_quando", columnDefinition="timestamp")
    private LocalDateTime criadoQuando;

    @Column(name = "atualizado_quando", columnDefinition="timestamp")
    private LocalDateTime atualizadoQuando;

    // Construtores
    public Jogo() {
        this.criadoQuando = LocalDateTime.now();
        this.atualizadoQuando = LocalDateTime.now();
        // Gera um id textual compatível com o esquema existente
        this.uid = UUID.randomUUID().toString();
    }

    public Jogo(String nome) {
        this();
        this.nome = nome;
    }

    public Jogo(String nome, String nomeAlternativo, LocalDate anoPublicacao, Integer tempoDeJogo,
                Integer minimoJogadores, Integer maximoJogadores, String codigoDeBarras, Boolean isDisponivel) {
        this(nome);
        this.nomeAlternativo = nomeAlternativo;
        this.anoPublicacao = anoPublicacao;
        this.tempoDeJogo = tempoDeJogo;
        this.minimoJogadores = minimoJogadores;
        this.maximoJogadores = maximoJogadores;
        this.codigoDeBarras = codigoDeBarras;
        this.isDisponivel = isDisponivel;
    }

    // Getters and Setters
    public String getUid() {
        return uid;
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

    public LocalDate getAnoPublicacao() {
        return anoPublicacao;
    }
    public void setAnoPublicacao(LocalDate anoPublicacao) {
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