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

import com.ludicom.backend.dto.JogoCreateRequest;
import com.ludicom.backend.dto.JogoResponse;
import com.ludicom.backend.dto.MessageResponse;
import com.ludicom.backend.service.JogoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/jogo")
@CrossOrigin(origins = "*") // mudar para o domínio do frontend em produção
@Validated
public class JogoController {

    private final JogoService jogoService;

    public JogoController(JogoService jogoService) {
        this.jogoService = jogoService;
    }

    /**
     * Criar um novo jogo
     */
    @PostMapping
    public ResponseEntity<JogoResponse> createJogo(@Valid @RequestBody JogoCreateRequest request) {
        JogoResponse response = jogoService.createJogo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Listar todos os jogos
     */
    @GetMapping
    public ResponseEntity<List<JogoResponse>> getAllJogos() {
        List<JogoResponse> jogos = jogoService.getAllJogos();
        return ResponseEntity.ok(jogos);
    }

    /**
     * Buscar jogo por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<JogoResponse> getJogoById(@PathVariable String id) {
        JogoResponse response = jogoService.getJogoById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Atualizar um jogo existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<JogoResponse> updateJogo(@PathVariable String id, @Valid @RequestBody JogoCreateRequest request) {
        JogoResponse response = jogoService.updateJogo(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletar um jogo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteJogo(@PathVariable String id) {
        MessageResponse response = jogoService.deleteJogo(id);
        return ResponseEntity.ok(response);
    }
}