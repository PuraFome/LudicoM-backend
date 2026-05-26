package com.ludicom.backend.specification;

import org.springframework.data.jpa.domain.Specification;

import com.ludicom.backend.model.Evento;

import jakarta.persistence.criteria.Predicate;

public class EventoSpecification {

    public static Specification<Evento> bySearchTerm(String searchTerm) {
        return (root, query, cb) -> {
            String pattern = "%" + searchTerm.toLowerCase() + "%";
            Predicate instituicaoNome = cb.like(cb.lower(root.join("instituicao").get("nome")), pattern);
            Predicate horaInicio = cb.like(cb.lower(root.get("horaInicio")), pattern);
            Predicate horaFim = cb.like(cb.lower(root.get("horaFim")), pattern);
            return cb.or(instituicaoNome, horaInicio, horaFim);
        };
    }
}
