package com.ludicom.backend.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.ludicom.backend.dto.EmprestimoCreateRequest;
import com.ludicom.backend.dto.EmprestimoResponse;
import com.ludicom.backend.dto.MessageResponse;
import com.ludicom.backend.service.EmprestimoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/emprestimo")
@CrossOrigin(origins = "*") // mudar para o domínio do frontend em produção
@Validated
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    /**
     * Criar um novo empréstimo
     */
    @PostMapping
    public ResponseEntity<EmprestimoResponse> createEmprestimo(@Valid @RequestBody EmprestimoCreateRequest request) {
        EmprestimoResponse response = emprestimoService.createEmprestimo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Listar todos os empréstimos
     */
    @GetMapping
    public ResponseEntity<List<EmprestimoResponse>> getAllEmprestimos() {
        List<EmprestimoResponse> emprestimos = emprestimoService.getAllEmprestimos();
        return ResponseEntity.ok(emprestimos);
    }

    /**
     * Buscar empréstimo por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoResponse> getEmprestimoById(@PathVariable String id) {
        EmprestimoResponse response = emprestimoService.getEmprestimoById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Atualizar um empréstimo existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmprestimoResponse> updateEmprestimo(@PathVariable String id,
            @Valid @RequestBody EmprestimoCreateRequest request) {
        EmprestimoResponse response = emprestimoService.updateEmprestimo(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletar um empréstimo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteEmprestimo(@PathVariable String id) {
        MessageResponse response = emprestimoService.deleteEmprestimo(id);
        return ResponseEntity.ok(response);
    }
}
