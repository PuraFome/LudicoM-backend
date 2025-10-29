package com.ludicom.backend.dto;

import java.time.LocalDate;

/**
 * Data Transfer Object for Evento responses.
 */
public class EventoResponse {

    private String uid;
    private InstituicaoResponse instituicao;
    private LocalDate data;
    private String horaInicio;
    private String horaFim;

    // Constructors
    public EventoResponse() {}

    public EventoResponse(String uid, InstituicaoResponse instituicao, LocalDate data, String horaInicio, String horaFim) {
        this.uid = uid;
        this.instituicao = instituicao;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    // Getters and Setters
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public InstituicaoResponse getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(InstituicaoResponse instituicao) {
        this.instituicao = instituicao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }
}

