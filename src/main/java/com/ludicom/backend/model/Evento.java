package com.ludicom.backend.model;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "text")
    private String uid;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_instituicao", nullable = false)
    private Instituicao instituicao;

    @NotNull(message = "Data do evento é obrigatória")
    @Column(name = "data", nullable = false, columnDefinition="date")
    private LocalDate data;

    @Column(name = "hora_inicio", nullable = false, columnDefinition="time")
    private String horaInicio;

    @Column(name = "hora_fim", nullable = false, columnDefinition="time")
    private String horaFim;

    public String getUid() {
        return uid;
    }

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public LocalDate getData() {
        return data;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }
}

