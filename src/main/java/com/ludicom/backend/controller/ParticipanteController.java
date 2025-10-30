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

import com.ludicom.backend.dto.ParticipanteCreateRequest;
import com.ludicom.backend.dto.ParticipanteResponse;
import com.ludicom.backend.dto.MessageResponse;
import com.ludicom.backend.service.ParticipanteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/participante")
@CrossOrigin(origins = "*") // mudar para o domínio do frontend em produção
@Validated
public class ParticipanteController {

    private final ParticipanteService participanteService;

    public ParticipanteController(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

    /**
     * Criar um novo participante
     * POST /api/participante
     */
    @PostMapping
    public ResponseEntity<ParticipanteResponse> createParticipante(@Valid @RequestBody ParticipanteCreateRequest request) {
        ParticipanteResponse response = participanteService.createParticipante(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Listar todos os participantes
     * GET /api/participante
     */
    @GetMapping
    public ResponseEntity<List<ParticipanteResponse>> getAllParticipantes() {
        List<ParticipanteResponse> participantes = participanteService.getAllParticipantes();
        return ResponseEntity.ok(participantes);
    }

    /**
     * Buscar participante por ID
     * GET /api/participante/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteResponse> getParticipanteById(@PathVariable String id) {
        ParticipanteResponse response = participanteService.getParticipanteById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Atualizar um participante existente
     * PUT /api/participante/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ParticipanteResponse> updateParticipante(@PathVariable String id, @Valid @RequestBody ParticipanteCreateRequest request) {
        ParticipanteResponse response = participanteService.updateParticipante(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletar um participante
     * DELETE /api/participante/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteParticipante(@PathVariable String id) {
        MessageResponse response = participanteService.deleteParticipante(id);
        return ResponseEntity.ok(response);
    }
}