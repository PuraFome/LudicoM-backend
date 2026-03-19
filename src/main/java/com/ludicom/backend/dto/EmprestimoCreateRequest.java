package com.ludicom.backend.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for Emprestimo creation requests.
 */
public class EmprestimoCreateRequest {

    @NotBlank(message = "ID do jogo é obrigatório")
    private String idJogo;

    @NotBlank(message = "ID do participante é obrigatório")
    private String idParticipante;

    @NotBlank(message = "ID do evento é obrigatório")
    private String idEvento;

    private String horaEmprestimo;

    private String horaDevolucao;

    private Boolean isDevolvido = false;

    // Constructors
    public EmprestimoCreateRequest() {
    }

    public EmprestimoCreateRequest(String idJogo, String idParticipante, String idEvento,
            String horaEmprestimo, String horaDevolucao, Boolean isDevolvido) {
        this.idJogo = idJogo;
        this.idParticipante = idParticipante;
        this.idEvento = idEvento;
        this.horaEmprestimo = horaEmprestimo;
        this.horaDevolucao = horaDevolucao;
        this.isDevolvido = isDevolvido;
    }

    // Getters and Setters
    public String getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(String idJogo) {
        this.idJogo = idJogo;
    }

    public String getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(String idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getHoraEmprestimo() {
        return horaEmprestimo;
    }

    public void setHoraEmprestimo(String horaEmprestimo) {
        this.horaEmprestimo = horaEmprestimo;
    }

    public String getHoraDevolucao() {
        return horaDevolucao;
    }

    public void setHoraDevolucao(String horaDevolucao) {
        this.horaDevolucao = horaDevolucao;
    }

    public Boolean getIsDevolvido() {
        return isDevolvido;
    }

    public void setIsDevolvido(Boolean isDevolvido) {
        this.isDevolvido = isDevolvido;
    }
}
