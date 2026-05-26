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

import com.ludicom.backend.dto.EventoCreateRequest;
import com.ludicom.backend.dto.EventoResponse;
import com.ludicom.backend.dto.MessageResponse;
import com.ludicom.backend.dto.PageResponse;
import com.ludicom.backend.service.EventoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/evento")
@CrossOrigin(origins = "*") // mudar para o domínio do frontend em produção
@Validated
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    /**
     * Criar um novo evento
     */
    @PostMapping
    public ResponseEntity<EventoResponse> createEvento(@Valid @RequestBody EventoCreateRequest request) {
        EventoResponse response = eventoService.createEvento(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Listar todos os eventos
     */
    @GetMapping
    public ResponseEntity<?> getAllEventos(
            @RequestParam(value = "paginated", defaultValue = "false") boolean paginated,
            @RequestParam(value = "search", defaultValue = "") String search,
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        
        if (paginated) {
            PageResponse<EventoResponse> eventos = eventoService.getEventosPaginated(pageable, search);
            return ResponseEntity.ok(eventos);
        } else {
            List<EventoResponse> eventos = eventoService.getAllEventos();
            return ResponseEntity.ok(eventos);
        }
    }

    /**
     * Buscar evento por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventoResponse> getEventoById(@PathVariable String id) {
        EventoResponse response = eventoService.getEventoById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Atualizar um evento existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<EventoResponse> updateEvento(@PathVariable String id, @Valid @RequestBody EventoCreateRequest request) {
        EventoResponse response = eventoService.updateEvento(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletar um evento
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteEvento(@PathVariable String id) {
        MessageResponse response = eventoService.deleteEvento(id);
        return ResponseEntity.ok(response);
    }

}
