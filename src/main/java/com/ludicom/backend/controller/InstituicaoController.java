package com.ludicom.backend.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ludicom.backend.dto.InstituicaoCreateRequest;
import com.ludicom.backend.dto.InstituicaoResponse;
import com.ludicom.backend.dto.MessageResponse;
import com.ludicom.backend.dto.PageResponse;
import com.ludicom.backend.service.InstituicaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/instituicao")
@CrossOrigin(origins = "*") // mudar para o domínio do frontend em produção
@Validated
public class InstituicaoController {

    private final InstituicaoService instituicaoService;

    public InstituicaoController(InstituicaoService instituicaoService) {
        this.instituicaoService = instituicaoService;
    }

    /**
     * Criar uma nova instituição
     */
    @PostMapping
    public ResponseEntity<InstituicaoResponse> createInstituicao(@Valid @RequestBody InstituicaoCreateRequest request) {
        InstituicaoResponse response = instituicaoService.createInstituicao(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Listar todas as instituições
     * GET /api/instituicao?paginated=true&page=0&size=20
     */
    @GetMapping
    public ResponseEntity<?> getAllInstituicoes(
            @RequestParam(value = "paginated", defaultValue = "false") boolean paginated,
            @RequestParam(value = "search", defaultValue = "") String search,
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        
        if (paginated) {
            PageResponse<InstituicaoResponse> instituicoes = instituicaoService.getInstituicoesPaginated(pageable, search);
            return ResponseEntity.ok(instituicoes);
        } else {
            List<InstituicaoResponse> instituicoes = instituicaoService.getAllInstituicoes();
            return ResponseEntity.ok(instituicoes);
        }
    }

    /**
     * Buscar instituição por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoResponse> getInstituicaoById(@PathVariable String id) {
        InstituicaoResponse response = instituicaoService.getInstituicaoById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Atualizar uma instituição existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<InstituicaoResponse> updateInstituicao(@PathVariable String id, @Valid @RequestBody InstituicaoCreateRequest request) {
        InstituicaoResponse response = instituicaoService.updateInstituicao(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletar uma instituição
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteInstituicao(@PathVariable String id) {
        MessageResponse response = instituicaoService.deleteInstituicao(id);
        return ResponseEntity.ok(response);
    }
}
