package com.ludicom.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ludicom.backend.dto.InstituicaoCreateRequest;
import com.ludicom.backend.dto.InstituicaoResponse;
import com.ludicom.backend.dto.MessageResponse;
import com.ludicom.backend.exception.RequiredFieldException;
import com.ludicom.backend.exception.ResourceAlreadyExistsException;
import com.ludicom.backend.exception.ResourceNotFoundException;
import com.ludicom.backend.model.Instituicao;
import com.ludicom.backend.repository.InstituicaoRepository;

@Service
@Transactional
public class InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;

    public InstituicaoService(InstituicaoRepository instituicaoRepository) {
        this.instituicaoRepository = instituicaoRepository;
    }

    /*
     * Criando uma nova instituição
     */
    public InstituicaoResponse createInstituicao(InstituicaoCreateRequest request) {
        if(request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new RequiredFieldException("Instituicao", "nome");
        }

        // Verifica se o nome da instituição já existe
        if (instituicaoRepository.existsByNome(request.getNome())) {
          throw new ResourceAlreadyExistsException("Instituicao", "nome", request.getNome());
        }

        Instituicao instituicao = new Instituicao(request.getNome(), request.getEndereco());
        Instituicao savedInstituicao = instituicaoRepository.save(instituicao);
        return convertToResponse(savedInstituicao);
    }

    @Transactional(readOnly = true)
    public List<InstituicaoResponse> getAllInstituicoes() {
        return instituicaoRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /*
     * Buscar instituição por ID
     */
    @Transactional(readOnly = true)
    public InstituicaoResponse getInstituicaoById(String id) {

        Instituicao instituicao = instituicaoRepository.findByUid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instituicao", "ID", id));
        return convertToResponse(instituicao);
    }

    /*
     * Atualizar uma instituição existente
     */
    public InstituicaoResponse updateInstituicao(String id, InstituicaoCreateRequest request) {
        if(request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new RequiredFieldException("Instituicao", "nome");
        }

        Instituicao instituicao = instituicaoRepository.findByUid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instituicao", "ID", id));

        // Verifica se o nome está sendo alterado e se já existe outra instituição com esse nome
        if (!instituicao.getNome().equals(request.getNome()) && instituicaoRepository.existsByNome(request.getNome())) {
              throw new ResourceAlreadyExistsException("Instituicao", "nome", request.getNome());
        }

        instituicao.setNome(request.getNome());
        instituicao.setEndereco(request.getEndereco());

        Instituicao updatedInstituicao = instituicaoRepository.save(instituicao);
        return convertToResponse(updatedInstituicao);
    }

    /*
     * Deletar uma instituição
     */
    public MessageResponse deleteInstituicao(String id) {

        if (!instituicaoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Instituicao", "ID", id);
        }
        instituicaoRepository.deleteById(id);
        return new MessageResponse("Instituição deletada com sucesso");
    }

    private InstituicaoResponse convertToResponse(Instituicao instituicao) {
        return new InstituicaoResponse(
            instituicao.getUid(),
            instituicao.getNome(),
            instituicao.getEndereco()
        );
    }
}
