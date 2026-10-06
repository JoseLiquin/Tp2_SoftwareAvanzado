package com.example.tp2.repository;
import com.example.tp2.model.AcumuladorExtraccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface AcumuladorExtraccionRepository extends JpaRepository<AcumuladorExtraccion, UUID> {
    Optional<AcumuladorExtraccion> findByUsuarioIdAndFecha(UUID usuarioId, LocalDate fecha);
}
