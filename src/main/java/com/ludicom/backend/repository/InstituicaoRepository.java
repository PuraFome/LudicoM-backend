package com.ludicom.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ludicom.backend.model.Instituicao;

@Repository
public interface InstituicaoRepository extends JpaRepository<Instituicao, String>, JpaSpecificationExecutor<Instituicao> {
    
    @Override
    Page<Instituicao> findAll(Pageable pageable);
    
    Optional<Instituicao> findByNome(String nome);
    
    boolean existsByNome(String nome);

    // JPQL deve referenciar o nome da entidade (classe), não o nome da tabela
    @Query("SELECT i FROM Instituicao i WHERE LOWER(i.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Instituicao> findByNomeContainingIgnoreCase(String nome);

    // Usando método padrão do JPA para buscar por uid
    Optional<Instituicao> findByUid(String uid);

}
