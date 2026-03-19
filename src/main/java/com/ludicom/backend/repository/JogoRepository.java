package com.ludicom.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ludicom.backend.model.Jogo;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, String> {
    
    Optional<Jogo> findByNome(String nome);
    
    boolean existsByNome(String nome);

    // JPQL deve referenciar o nome da entidade (classe), não o nome da tabela
    @Query("SELECT j FROM Jogo j WHERE LOWER(j.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Jogo> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT j FROM Jogo j WHERE j.uid = :uid")
    Optional<Jogo> findByUid(String uid);

    Optional<Jogo> findByCodigoDeBarras(String codigoDeBarras);

}