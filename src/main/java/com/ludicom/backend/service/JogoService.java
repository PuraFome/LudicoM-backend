package com.ludicom.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ludicom.backend.dto.JogoCreateRequest;
import com.ludicom.backend.dto.JogoResponse;
import com.ludicom.backend.dto.MessageResponse;
import com.ludicom.backend.exception.RequiredFieldException;
import com.ludicom.backend.exception.ResourceAlreadyExistsException;
import com.ludicom.backend.exception.ResourceNotFoundException;
import com.ludicom.backend.model.Jogo;
import com.ludicom.backend.repository.JogoRepository;

@Service
@Transactional
public class JogoService {

    private final JogoRepository jogoRepository;

    public JogoService(JogoRepository jogoRepository) {
        this.jogoRepository = jogoRepository;
    }

    /*
     * Criando um novo jogo
     */
    public JogoResponse createJogo(JogoCreateRequest request) {
        if(request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new RequiredFieldException("Jogo", "nome");
        }

        // Verifica se o nome do jogo já existe
        if (jogoRepository.existsByNome(request.getNome())) {
          throw new ResourceAlreadyExistsException("Jogo", "nome", request.getNome());
        }

        Jogo jogo = new Jogo(request.getNome(), request.getNomeAlternativo(), request.getAnoPublicacao(),
                             request.getTempoDeJogo(), request.getMinimoJogadores(), request.getMaximoJogadores(),
                             request.getCodigoDeBarras(), request.getIsDisponivel());
        Jogo savedJogo = jogoRepository.save(jogo);
        return convertToResponse(savedJogo);
    }

    @Transactional(readOnly = true)
    public List<JogoResponse> getAllJogos() {
        return jogoRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /*
     * Buscar jogo por ID
     */
    @Transactional(readOnly = true)
    public JogoResponse getJogoById(String id) {
        Jogo jogo = jogoRepository.findByUid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo", "ID", id));
        return convertToResponse(jogo);
    }

    /*
     * Atualizar um jogo existente
     */
    public JogoResponse updateJogo(String id, JogoCreateRequest request) {
        if(request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new RequiredFieldException("Jogo", "nome");
        }

        Jogo jogo = jogoRepository.findByUid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo", "ID", id));

        // Verifica se o nome está sendo alterado e se já existe outro jogo com esse nome
        if (!jogo.getNome().equals(request.getNome()) && jogoRepository.existsByNome(request.getNome())) {
              throw new ResourceAlreadyExistsException("Jogo", "nome", request.getNome());
        }

        jogo.setNome(request.getNome());
        jogo.setNomeAlternativo(request.getNomeAlternativo());
        jogo.setAnoPublicacao(request.getAnoPublicacao());
        jogo.setTempoDeJogo(request.getTempoDeJogo());
        jogo.setMinimoJogadores(request.getMinimoJogadores());
        jogo.setMaximoJogadores(request.getMaximoJogadores());
        jogo.setCodigoDeBarras(request.getCodigoDeBarras());
        jogo.setIsDisponivel(request.getIsDisponivel());

        Jogo updatedJogo = jogoRepository.save(jogo);
        return convertToResponse(updatedJogo);
    }

    /*
     * Deletar um jogo
     */
    public MessageResponse deleteJogo(String id) {
        if (!jogoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Jogo", "ID", id);
        }
        jogoRepository.deleteById(id);
        return new MessageResponse("Jogo deletado com sucesso");
    }

    private JogoResponse convertToResponse(Jogo jogo) {
        return new JogoResponse(
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
            jogo.getAtualizadoQuando()
        );
    }
}