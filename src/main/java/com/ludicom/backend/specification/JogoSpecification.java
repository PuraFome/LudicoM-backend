package com.ludicom.backend.specification;

import org.springframework.data.jpa.domain.Specification;

import com.ludicom.backend.model.Jogo;

import jakarta.persistence.criteria.Predicate;

public class JogoSpecification {

    public static Specification<Jogo> bySearchTerm(String searchTerm) {
        return (root, query, cb) -> {
            String pattern = "%" + searchTerm.toLowerCase() + "%";
            Predicate nome = cb.like(cb.lower(root.get("nome")), pattern);
            Predicate nomeAlternativo = cb.like(cb.lower(root.get("nomeAlternativo")), pattern);
            Predicate codigoDeBarras = cb.like(cb.lower(root.get("codigoDeBarras")), pattern);
            return cb.or(nome, nomeAlternativo, codigoDeBarras);
        };
    }
}
