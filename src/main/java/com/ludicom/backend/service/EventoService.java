package com.ludicom.backend.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ludicom.backend.dto.EventoCreateRequest;
import com.ludicom.backend.dto.EventoResponse;
import com.ludicom.backend.dto.InstituicaoResponse;
import com.ludicom.backend.dto.MessageResponse;
import com.ludicom.backend.dto.PageResponse;
import com.ludicom.backend.exception.InvalidDateTimeFormatException;
import com.ludicom.backend.exception.RequiredFieldException;
import com.ludicom.backend.exception.ResourceNotFoundException;
import com.ludicom.backend.model.Evento;
import com.ludicom.backend.model.Instituicao;
import com.ludicom.backend.repository.EventoRepository;
import com.ludicom.backend.repository.InstituicaoRepository;
import com.ludicom.backend.specification.EventoSpecification;

@Service
@Transactional
public class EventoService {

    private static final Pattern SHORT_TIME_PATTERN = Pattern.compile("^([01]\\d|2[0-3]):([0-5]\\d)$");

    private final EventoRepository eventoRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final com.ludicom.backend.repository.EmprestimoRepository emprestimoRepository;

    public EventoService(EventoRepository eventoRepository, InstituicaoRepository instituicaoRepository,
            com.ludicom.backend.repository.EmprestimoRepository emprestimoRepository) {
        this.eventoRepository = eventoRepository;
        this.instituicaoRepository = instituicaoRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    /**
     * Normaliza formato de hora, adicionando :00 se estiver no formato HH:MM
     */
    private String normalizeTimeFormat(String time) {
        if (time != null && SHORT_TIME_PATTERN.matcher(time).matches()) {
            return time + ":00";
        }
        return time;
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
        evento.setHoraInicio(normalizeTimeFormat(request.getHoraInicio()));
        evento.setHoraFim(normalizeTimeFormat(request.getHoraFim()));

        Evento savedEvento = eventoRepository.save(evento);
        
        // Converter usando a instituição que já temos em memória
        return convertToResponse(savedEvento);
    }

    @Transactional(readOnly = true)
    public List<EventoResponse> getAllEventos() {
        return eventoRepository.findAll().stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PageResponse<EventoResponse> getEventosPaginated(Pageable pageable, String search) {
        Specification<Evento> spec = Specification.where(null);
        if (search != null && !search.isBlank()) {
            spec = EventoSpecification.bySearchTerm(search);
        }
        Page<Evento> page = eventoRepository.findAll(spec, pageable);
        return new PageResponse<>(
                page.getContent().stream().map(this::convertToResponse).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst()
        );
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
        evento.setHoraInicio(normalizeTimeFormat(request.getHoraInicio()));
        evento.setHoraFim(normalizeTimeFormat(request.getHoraFim()));

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
        
        // Exclui os empréstimos associados em uma única operação de banco.
        emprestimoRepository.deleteByEventoId(id);
        
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
