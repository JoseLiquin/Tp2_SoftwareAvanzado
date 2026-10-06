package com.example.tp2.repository;
import com.example.tp2.model.Adherente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AdherenteRepository extends JpaRepository<Adherente, UUID> {
    List<Adherente> findByCuentaId(UUID cuentaId);
}