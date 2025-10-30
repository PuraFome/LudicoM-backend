package com.ludicom.backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ludicom.backend.model.Participante;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, String> {

    Optional<Participante> findByEmail(String email);

    Optional<Participante> findByDocumento(String documento);

    Optional<Participante> findByRa(String ra);

    boolean existsByEmail(String email);

    boolean existsByDocumento(String documento);

    boolean existsByRa(String ra);
}
