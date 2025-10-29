package com.ludicom.backend.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ludicom.backend.dto.EventoCreateRequest;
import com.ludicom.backend.dto.EventoResponse;
import com.ludicom.backend.dto.InstituicaoResponse;
import com.ludicom.backend.dto.MessageResponse;
import com.ludicom.backend.exception.InvalidDateTimeFormatException;
import com.ludicom.backend.exception.RequiredFieldException;
import com.ludicom.backend.exception.ResourceNotFoundException;
import com.ludicom.backend.model.Evento;
import com.ludicom.backend.model.Instituicao;
import com.ludicom.backend.repository.EventoRepository;
import com.ludicom.backend.repository.InstituicaoRepository;

@Service
@Transactional
public class EventoService {

    private final EventoRepository eventoRepository;
    private final InstituicaoRepository instituicaoRepository;

    public EventoService(EventoRepository eventoRepository, InstituicaoRepository instituicaoRepository) {
        this.eventoRepository = eventoRepository;
        this.instituicaoRepository = instituicaoRepository;
    }

    /*
     * Criando um novo evento
     */
    public EventoResponse createEvento(EventoCreateRequest request) {
        if(request.getData() == null || request.getData().trim().isEmpty()) {
            throw new RequiredFieldException("Evento", "data");
        }

        if(request.getIdInstituicao() == null || request.getIdInstituicao().trim().isEmpty()) {
            throw new RequiredFieldException("Evento", "idInstituicao");
        }

        // Verifica se a instituição existe
        Instituicao instituicao = instituicaoRepository.findById(request.getIdInstituicao())
                .orElseThrow(() -> new ResourceNotFoundException("Instituicao", "ID", request.getIdInstituicao()));

        // Parse da data com tratamento de formato inválido
        LocalDate dataEvento;
        try {
            dataEvento = LocalDate.parse(request.getData()); // ISO-8601: yyyy-MM-dd
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeFormatException("data", "Data deve estar no formato yyyy-MM-dd");
        }

        Evento evento = new Evento();
        evento.setInstituicao(instituicao);
        evento.setData(dataEvento);
        evento.setHoraInicio(request.getHoraInicio());
        evento.setHoraFim(request.getHoraFim());

        Evento savedEvento = eventoRepository.save(evento);
        
        // Converter usando a instituição que já temos em memória
        return convertToResponse(savedEvento);
    }

    @Transactional(readOnly = true)
    public List<EventoResponse> getAllEventos() {
        return eventoRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /*
     * Buscar evento por ID
     */
    @Transactional(readOnly = true)
    public EventoResponse getEventoById(String id) {
        Evento evento = eventoRepository.findByUid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", "ID", id));
        return convertToResponse(evento);
    }

    /*
     * Atualizar um evento existente
     */
    public EventoResponse updateEvento(String id, EventoCreateRequest request) {
        if (request.getData() == null || request.getData().trim().isEmpty()) {
            throw new RequiredFieldException("Evento", "data");
        }

        if (request.getIdInstituicao() == null || request.getIdInstituicao().trim().isEmpty()) {
            throw new RequiredFieldException("Evento", "idInstituicao");
        }

        Evento evento = eventoRepository.findByUid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", "ID", id));

        // Verifica se a instituição existe
        Instituicao instituicao = instituicaoRepository.findById(request.getIdInstituicao())
                .orElseThrow(() -> new ResourceNotFoundException("Instituicao", "ID", request.getIdInstituicao()));

        // Parse da data com tratamento de formato inválido
        LocalDate dataEvento;
        try {
            dataEvento = LocalDate.parse(request.getData());
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeFormatException("data", "Data deve estar no formato yyyy-MM-dd");
        }

        evento.setInstituicao(instituicao);
        evento.setData(dataEvento);
        evento.setHoraInicio(request.getHoraInicio());
        evento.setHoraFim(request.getHoraFim());

        Evento updatedEvento = eventoRepository.save(evento);
        
        // Converter usando a instituição que já temos em memória
        return convertToResponse(updatedEvento);
    }

    /*
     * Deletar um evento
     */
    public MessageResponse deleteEvento(String id) {
        if (!eventoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Evento", "ID", id);
        }
        eventoRepository.deleteById(id);
        return new MessageResponse("Evento deletado com sucesso");
    }

    private EventoResponse convertToResponse(Evento evento) {
        InstituicaoResponse instituicaoResponse = new InstituicaoResponse(
            evento.getInstituicao().getUid(),
            evento.getInstituicao().getNome(),
            evento.getInstituicao().getEndereco()
        );

        return new EventoResponse(
            evento.getUid(),
            instituicaoResponse,
            evento.getData(),
            evento.getHoraInicio(),
            evento.getHoraFim()
        );
    }

}
