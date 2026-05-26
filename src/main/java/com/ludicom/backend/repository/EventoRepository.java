package com.ludicom.backend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ludicom.backend.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, String>, JpaSpecificationExecutor<Evento> {

    @Override
    @EntityGraph(attributePaths = {"instituicao"})
    List<Evento> findAll();

    @EntityGraph(attributePaths = {"instituicao"})
    Page<Evento> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"instituicao"})
    Page<Evento> findAll(Specification<Evento> spec, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"instituicao"})
    Optional<Evento> findById(String id);

    @EntityGraph(attributePaths = {"instituicao"})
    @Query("SELECT e FROM Evento e WHERE e.uid = :uid")
    Optional<Evento> findByUid(String uid);

    @Query("SELECT e FROM Evento e WHERE e.data = :data")
    List<Evento> findByData(LocalDate data);

    @Query("SELECT e FROM Evento e WHERE e.instituicao.uid = :instituicaoId")
    List<Evento> findByInstituicaoId(String instituicaoId);

    @Query("SELECT e FROM Evento e WHERE e.data >= :dataInicio AND e.data <= :dataFim")
    List<Evento> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);

    // Métodos para bloquear exclusão de instituição se houver qualquer evento
    // associado
    boolean existsByInstituicaoUid(String uid);

    long countByInstituicaoUid(String uid);
}
