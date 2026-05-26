package com.ludicom.backend.specification;

import org.springframework.data.jpa.domain.Specification;

import com.ludicom.backend.model.Instituicao;

import jakarta.persistence.criteria.Predicate;

public class InstituicaoSpecification {

    public static Specification<Instituicao> bySearchTerm(String searchTerm) {
        return (root, query, cb) -> {
            String pattern = "%" + searchTerm.toLowerCase() + "%";
            Predicate nome = cb.like(cb.lower(root.get("nome")), pattern);
            Predicate endereco = cb.like(cb.lower(root.get("endereco")), pattern);
            return cb.or(nome, endereco);
        };
    }
}
