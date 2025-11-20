package com.ludicom.backend.repository;

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

}
