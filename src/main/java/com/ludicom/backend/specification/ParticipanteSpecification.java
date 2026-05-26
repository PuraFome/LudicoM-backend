package com.ludicom.backend.specification;

import org.springframework.data.jpa.domain.Specification;

import com.ludicom.backend.model.Participante;

import jakarta.persistence.criteria.Predicate;

public class ParticipanteSpecification {

    public static Specification<Participante> bySearchTerm(String searchTerm) {
        return (root, query, cb) -> {
            String pattern = "%" + searchTerm.toLowerCase() + "%";
            Predicate nome = cb.like(cb.lower(root.get("nome")), pattern);
            Predicate email = cb.like(cb.lower(root.get("email")), pattern);
            Predicate documento = cb.like(cb.lower(root.get("documento")), pattern);
            Predicate ra = cb.like(cb.lower(root.get("ra")), pattern);
            return cb.or(nome, email, documento, ra);
        };
    }
}
