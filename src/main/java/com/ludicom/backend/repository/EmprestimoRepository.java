package com.ludicom.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ludicom.backend.model.Emprestimo;
import com.ludicom.backend.model.Participante;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, String> {

    @Query("SELECT e FROM Emprestimo e WHERE e.participante = :participante AND e.isDevolvido = false")
    Optional<Emprestimo> findActiveEmprestimoByParticipante(@Param("participante") Participante participante);

    @Query("SELECT e FROM Emprestimo e JOIN e.jogo j WHERE j.codigoDeBarras = :codigoDeBarras AND e.isDevolvido = false")
    Optional<Emprestimo> findActiveEmprestimoByCodigoDeBarras(@Param("codigoDeBarras") String codigoDeBarras);

    @Query("SELECT e FROM Emprestimo e WHERE e.evento.uid = :eventoId")
    List<Emprestimo> findByEventoId(@Param("eventoId") String eventoId);

    // Verifica se existe empréstimo ativo (não devolvido) para um jogo específico
    boolean existsByJogoUidAndIsDevolvidoFalse(String uid);

    // Conta quantos empréstimos ativos (não devolvidos) referenciam o jogo
    long countByJogoUidAndIsDevolvidoFalse(String uid);

    // Considera também registros onde isDevolvido está null (tratados como ativos)
    @Query("SELECT COUNT(e) > 0 FROM Emprestimo e WHERE e.jogo.uid = :uid AND (e.isDevolvido = false OR e.isDevolvido IS NULL)")
    boolean hasActiveOrUnknownEmprestimoForJogo(@Param("uid") String uid);

    @Query("SELECT COUNT(e) FROM Emprestimo e WHERE e.jogo.uid = :uid AND (e.isDevolvido = false OR e.isDevolvido IS NULL)")
    long countActiveOrUnknownEmprestimosForJogo(@Param("uid") String uid);

    // Qualquer referência de empréstimo ao jogo (ativo ou histórico)
    boolean existsByJogoUid(String uid);

    long countByJogoUid(String uid);

}
