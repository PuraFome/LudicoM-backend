package com.ludicom.backend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ludicom.backend.model.Participante;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, String>, JpaSpecificationExecutor<Participante> {

    @Override
    @EntityGraph(attributePaths = {"instituicao"})
    java.util.List<Participante> findAll();

    @EntityGraph(attributePaths = {"instituicao"})
    Page<Participante> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"instituicao"})
    Page<Participante> findAll(Specification<Participante> spec, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"instituicao"})
    Optional<Participante> findById(String id);

    Optional<Participante> findByEmail(String email);

    Optional<Participante> findByDocumento(String documento);

    Optional<Participante> findByRa(String ra);

    boolean existsByEmail(String email);

    boolean existsByDocumento(String documento);

    boolean existsByRa(String ra);
}
