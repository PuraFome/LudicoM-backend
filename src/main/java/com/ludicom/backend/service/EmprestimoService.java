package com.ludicom.backend.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ludicom.backend.dto.DevolucaoRequest;
import com.ludicom.backend.dto.EmprestimoCreateRequest;
import com.ludicom.backend.dto.EmprestimoResponse;
import com.ludicom.backend.dto.EventoResponse;
import com.ludicom.backend.dto.InstituicaoResponse;
import com.ludicom.backend.dto.JogoResponse;
import com.ludicom.backend.dto.MessageResponse;
import com.ludicom.backend.dto.ParticipanteResponse;
import com.ludicom.backend.exception.RequiredFieldException;
import com.ludicom.backend.exception.ResourceAlreadyExistsException;
import com.ludicom.backend.exception.ResourceNotFoundException;
import com.ludicom.backend.exception.ResourceUnavailableException;
import com.ludicom.backend.model.Emprestimo;
import com.ludicom.backend.model.Evento;
import com.ludicom.backend.model.Jogo;
import com.ludicom.backend.model.Participante;
import com.ludicom.backend.repository.EmprestimoRepository;
import com.ludicom.backend.repository.EventoRepository;
import com.ludicom.backend.repository.JogoRepository;
import com.ludicom.backend.repository.ParticipanteRepository;

@Service
@Transactional
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final JogoRepository jogoRepository;
    private final ParticipanteRepository participanteRepository;
    private final EventoRepository eventoRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
            JogoRepository jogoRepository,
            ParticipanteRepository participanteRepository,
            EventoRepository eventoRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.jogoRepository = jogoRepository;
        this.participanteRepository = participanteRepository;
        this.eventoRepository = eventoRepository;
    }

    /**
     * Criar um novo empréstimo
     */
    public EmprestimoResponse createEmprestimo(EmprestimoCreateRequest request) {
        // Validações
        if (request.getIdJogo() == null || request.getIdJogo().trim().isEmpty()) {
            throw new RequiredFieldException("Emprestimo", "idJogo");
        }
        if (request.getIdParticipante() == null || request.getIdParticipante().trim().isEmpty()) {
            throw new RequiredFieldException("Emprestimo", "idParticipante");
        }
        if (request.getIdEvento() == null || request.getIdEvento().trim().isEmpty()) {
            throw new RequiredFieldException("Emprestimo", "idEvento");
        }

        // Definir hora atual se não fornecida
        String horaEmprestimo = request.getHoraEmprestimo();
        if (horaEmprestimo == null || horaEmprestimo.trim().isEmpty()) {
            horaEmprestimo = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }

        // Buscar entidades relacionadas
        Jogo jogo = jogoRepository.findById(request.getIdJogo())
                .orElseThrow(() -> new ResourceNotFoundException("Jogo", "ID", request.getIdJogo()));

        // Verificar se o jogo está disponível
        if (!jogo.getIsDisponivel()) {
            String mensagem = String.format("O jogo %s não está disponível para emprestimo", jogo.getNome());
            throw new ResourceUnavailableException(mensagem);
        }

        Participante participante = participanteRepository.findById(request.getIdParticipante())
                .orElseThrow(() -> new ResourceNotFoundException("Participante", "ID", request.getIdParticipante()));

        Evento evento = eventoRepository.findById(request.getIdEvento())
                .orElseThrow(() -> new ResourceNotFoundException("Evento", "ID", request.getIdEvento()));

        // Verificar se o participante já tem um empréstimo ativo
        emprestimoRepository.findActiveEmprestimoByParticipante(participante).ifPresent(emprestimoAtivo -> {
            String mensagem = String.format("O usuário %s, já possui um empréstimo ativo, com o jogo %s",
                    participante.getNome(),
                    emprestimoAtivo.getJogo().getNome());
            throw new ResourceAlreadyExistsException(mensagem);
        });

        // Criar empréstimo
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setJogo(jogo);
        emprestimo.setParticipante(participante);
        emprestimo.setEvento(evento);
        emprestimo.setHoraEmprestimo(horaEmprestimo);
        emprestimo.setHoraDevolucao(request.getHoraDevolucao());
        emprestimo.setDevolvido(request.getIsDevolvido() != null ? request.getIsDevolvido() : Boolean.FALSE);

        // Marcar jogo como indisponível
        jogo.setIsDisponivel(false);
        jogoRepository.save(jogo);

        Emprestimo savedEmprestimo = emprestimoRepository.save(emprestimo);
        return convertToResponse(savedEmprestimo);
    }

    /**
     * Listar todos os empréstimos
     */
    @Transactional(readOnly = true)
    public List<EmprestimoResponse> getAllEmprestimos() {
        return emprestimoRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Buscar empréstimo por ID
     */
    @Transactional(readOnly = true)
    public EmprestimoResponse getEmprestimoById(String id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emprestimo", "ID", id));

        return convertToResponse(emprestimo);
    }

    /**
     * Atualizar um empréstimo existente
     */
    public EmprestimoResponse updateEmprestimo(String id, EmprestimoCreateRequest request) {
        // Validações
        if (request.getIdJogo() == null || request.getIdJogo().trim().isEmpty()) {
            throw new RequiredFieldException("Emprestimo", "idJogo");
        }
        if (request.getIdParticipante() == null || request.getIdParticipante().trim().isEmpty()) {
            throw new RequiredFieldException("Emprestimo", "idParticipante");
        }
        if (request.getIdEvento() == null || request.getIdEvento().trim().isEmpty()) {
            throw new RequiredFieldException("Emprestimo", "idEvento");
        }

        // Definir hora atual se não fornecida
        String horaEmprestimo = request.getHoraEmprestimo();
        if (horaEmprestimo == null || horaEmprestimo.trim().isEmpty()) {
            horaEmprestimo = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }

        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emprestimo", "ID", id));

        // Buscar entidades relacionadas
        Jogo jogo = jogoRepository.findById(request.getIdJogo())
                .orElseThrow(() -> new ResourceNotFoundException("Jogo", "ID", request.getIdJogo()));

        Participante participante = participanteRepository.findById(request.getIdParticipante())
                .orElseThrow(() -> new ResourceNotFoundException("Participante", "ID", request.getIdParticipante()));

        Evento evento = eventoRepository.findById(request.getIdEvento())
                .orElseThrow(() -> new ResourceNotFoundException("Evento", "ID", request.getIdEvento()));

        // Atualizar empréstimo
        emprestimo.setJogo(jogo);
        emprestimo.setParticipante(participante);
        emprestimo.setEvento(evento);
        emprestimo.setHoraEmprestimo(horaEmprestimo);
        emprestimo.setHoraDevolucao(request.getHoraDevolucao());
        emprestimo.setDevolvido(request.getIsDevolvido() != null ? request.getIsDevolvido() : Boolean.FALSE);

        Emprestimo updatedEmprestimo = emprestimoRepository.save(emprestimo);
        return convertToResponse(updatedEmprestimo);
    }

    /**
     * Processar devolução de empréstimo
     */
    public EmprestimoResponse processarDevolucao(DevolucaoRequest request) {
        // Validação
        if (request.getCodigoDeBarras() == null || request.getCodigoDeBarras().trim().isEmpty()) {
            throw new RequiredFieldException("Devolucao", "codigoDeBarras");
        }

        // Buscar empréstimo ativo pelo código de barras
        Emprestimo emprestimo = emprestimoRepository.findActiveEmprestimoByCodigoDeBarras(request.getCodigoDeBarras())
                .orElseThrow(() -> new ResourceNotFoundException("Emprestimo ativo", "código de barras", request.getCodigoDeBarras()));

        // Marcar como devolvido e registrar hora de devolução
        emprestimo.setDevolvido(true);
        emprestimo.setHoraDevolucao(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        // Marcar jogo como disponível novamente
        Jogo jogo = emprestimo.getJogo();
        jogo.setIsDisponivel(true);
        jogoRepository.save(jogo);

        Emprestimo updatedEmprestimo = emprestimoRepository.save(emprestimo);
        return convertToResponse(updatedEmprestimo);
    }

    /**
     * Deletar um empréstimo
     */
    public MessageResponse deleteEmprestimo(String id) {
        if (!emprestimoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Emprestimo", "ID", id);
        }

        emprestimoRepository.deleteById(id);
        return new MessageResponse("Empréstimo deletado com sucesso");
    }

    /**
     * Converter Emprestimo para EmprestimoResponse
     */
    private EmprestimoResponse convertToResponse(Emprestimo emprestimo) {
        // Converter Jogo
        Jogo jogo = emprestimo.getJogo();
        JogoResponse jogoResponse = new JogoResponse(
                jogo.getUid(),
                jogo.getNome(),
                jogo.getNomeAlternativo(),
                jogo.getAnoPublicacao() != null ? jogo.getAnoPublicacao().toString() : null,
                jogo.getTempoDeJogo(),
                jogo.getMinimoJogadores(),
                jogo.getMaximoJogadores(),
                jogo.getCodigoDeBarras(),
                jogo.getIsDisponivel(),
                jogo.getCriadoQuando(),
                jogo.getAtualizadoQuando());

        // Converter Participante
        Participante participante = emprestimo.getParticipante();
        ParticipanteResponse participanteResponse = new ParticipanteResponse(
                participante.getId(),
                participante.getNome(),
                participante.getEmail(),
                participante.getDocumento(),
                participante.getInstituicao(),
                participante.getRa());

        // Converter Evento
        Evento evento = emprestimo.getEvento();
        InstituicaoResponse instituicaoResponse = null;
        if (evento.getInstituicao() != null) {
            instituicaoResponse = new InstituicaoResponse(
                    evento.getInstituicao().getUid(),
                    evento.getInstituicao().getNome(),
                    evento.getInstituicao().getEndereco());
        }
        EventoResponse eventoResponse = new EventoResponse(
                evento.getUid(),
                instituicaoResponse,
                evento.getData(),
                evento.getHoraInicio(),
                evento.getHoraFim());

        return new EmprestimoResponse(
                emprestimo.getUid(),
                jogoResponse,
                participanteResponse,
                eventoResponse,
                emprestimo.getHoraEmprestimo(),
                emprestimo.getHoraDevolucao(),
                emprestimo.getDevolvido());
    }
}
