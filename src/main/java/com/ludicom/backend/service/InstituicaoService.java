package com.ludicom.backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ludicom.backend.dto.InstituicaoCreateRequest;
import com.ludicom.backend.dto.InstituicaoResponse;
import com.ludicom.backend.dto.MessageResponse;
import com.ludicom.backend.dto.PageResponse;
import com.ludicom.backend.exception.RequiredFieldException;
import com.ludicom.backend.exception.ResourceAlreadyExistsException;
import com.ludicom.backend.exception.ResourceInUseException;
import com.ludicom.backend.exception.ResourceNotFoundException;
import com.ludicom.backend.model.Instituicao;
import com.ludicom.backend.repository.EventoRepository;
import com.ludicom.backend.repository.InstituicaoRepository;
import com.ludicom.backend.specification.InstituicaoSpecification;

@Service
@Transactional
public class InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;
    private final EventoRepository eventoRepository;

    public InstituicaoService(InstituicaoRepository instituicaoRepository, EventoRepository eventoRepository) {
        this.instituicaoRepository = instituicaoRepository;
        this.eventoRepository = eventoRepository;
    }

    /*
     * Criando uma nova instituição
     */
    public InstituicaoResponse createInstituicao(InstituicaoCreateRequest request) {
        if (request.getNome() == null || request.getNome().trim().isEmpty()) {
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
                .toList();
    }

    @Transactional(readOnly = true)
    public PageResponse<InstituicaoResponse> getInstituicoesPaginated(Pageable pageable, String search) {
        Specification<Instituicao> spec = Specification.where(null);
        if (search != null && !search.isBlank()) {
            spec = InstituicaoSpecification.bySearchTerm(search);
        }
        Page<Instituicao> page = instituicaoRepository.findAll(spec, pageable);
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
        if (request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new RequiredFieldException("Instituicao", "nome");
        }

        Instituicao instituicao = instituicaoRepository.findByUid(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instituicao", "ID", id));

        // Verifica se o nome está sendo alterado e se já existe outra instituição com
        // esse nome
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

        // Bloqueia se houver qualquer evento associado à instituição
        if (eventoRepository.existsByInstituicaoUid(id)) {
            throw new ResourceInUseException("Instituicao");
        }

        instituicaoRepository.deleteById(id);
        return new MessageResponse("Instituição deletada com sucesso");
    }

    private InstituicaoResponse convertToResponse(Instituicao instituicao) {
        return new InstituicaoResponse(
                instituicao.getUid(),
                instituicao.getNome(),
                instituicao.getEndereco());
    }
}
