package com.ludicom.backend.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "emprestimo")

public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "text")
    private String uid;

    @NotBlank(message = "Participante é obrigatório")
    @OneToOne(fetch  = FetchType.LAZY)
    @JoinColumn(name = "id_participante", nullable = false)
    private Participante participante;

    @NotBlank(message = "Jogo é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jogo", nullable = false)
    private Jogo jogo;

    @NotBlank(message = "Evento é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    @NotBlank(message = "Horário do empréstimo é obrigatório")
    @Column(name = "hora_emprestimo", nullable = false, columnDefinition="time")
    private String horaEmprestimo;

    @NotBlank(message = "Horário da devolução é obrigatório")
    @Column(name = "hora_devolucao", nullable = false, columnDefinition="time")
    private String horaDevolucao;

    @Column(name = "is_devolvido", nullable = false)
    private Boolean isDevolvido;

    public String getUid() {
        return uid;
    }

    public Participante getParticipante() {
        return participante;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public Evento getEvento() {
        return evento;
    }

    public String getHoraEmprestimo() {
        return horaEmprestimo;
    }

    public String getHoraDevolucao() {
        return horaDevolucao;
    }

    public Boolean getDevolvido() {
        return isDevolvido;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public void setHoraEmprestimo(String horaEmprestimo) {
        this.horaEmprestimo = horaEmprestimo;
    }

    public void setHoraDevolucao(String horaDevolucao) {
        this.horaDevolucao = horaDevolucao;
    }

    public void setDevolvido(Boolean devolvido) {
        isDevolvido = devolvido;
    }
}
