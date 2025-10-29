package com.ludicom.backend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ludicom.backend.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, String> {
    
    @Query("SELECT e FROM Evento e WHERE e.uid = :uid")
    Optional<Evento> findByUid(String uid);

    @Query("SELECT e FROM Evento e WHERE e.data = :data")
    List<Evento> findByData(LocalDate data);

    @Query("SELECT e FROM Evento e WHERE e.instituicao.uid = :instituicaoId")
    List<Evento> findByInstituicaoId(String instituicaoId);

    @Query("SELECT e FROM Evento e WHERE e.data >= :dataInicio AND e.data <= :dataFim")
    List<Evento> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);
}
