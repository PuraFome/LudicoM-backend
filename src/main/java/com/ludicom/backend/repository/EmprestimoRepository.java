package com.ludicom.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ludicom.backend.model.Emprestimo;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, String> {

    @Override
    @EntityGraph(attributePaths = {"jogo", "participante", "participante.instituicao", "evento", "evento.instituicao"})
    List<Emprestimo> findAll();

    @EntityGraph(attributePaths = {"jogo", "participante", "participante.instituicao", "evento", "evento.instituicao"})
    Page<Emprestimo> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"jogo", "participante", "participante.instituicao", "evento", "evento.instituicao"})
    Optional<Emprestimo> findById(String id);

    @Query("SELECT e FROM Emprestimo e WHERE e.evento.uid = :eventoId")
    @EntityGraph(attributePaths = {"jogo", "participante", "participante.instituicao", "evento"})
    List<Emprestimo> findByEventoId(@Param("eventoId") String eventoId);

    @Modifying
    @Query("DELETE FROM Emprestimo e WHERE e.evento.uid = :eventoId")
    int deleteByEventoId(@Param("eventoId") String eventoId);

    @Query("SELECT e FROM Emprestimo e WHERE e.jogo.uid = :jogoId")
    @EntityGraph(attributePaths = {"jogo", "participante", "evento"})
    List<Emprestimo> findByJogoId(@Param("jogoId") String jogoId);

    @Query("SELECT e FROM Emprestimo e WHERE e.participante.uid = :participanteId")
    @EntityGraph(attributePaths = {"jogo", "participante", "evento"})
    List<Emprestimo> findByParticipanteId(@Param("participanteId") String participanteId);

    @Query("SELECT e FROM Emprestimo e WHERE e.isDevolvido = false")
    @EntityGraph(attributePaths = {"jogo", "participante", "evento"})
    List<Emprestimo> findAllNaoDevolvidos();

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Emprestimo e WHERE e.jogo.uid = :jogoId")
    boolean existsByJogoUid(@Param("jogoId") String jogoId);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Emprestimo e WHERE e.participante.uid = :participanteId")
    boolean existsByParticipanteUid(@Param("participanteId") String participanteId);
}
