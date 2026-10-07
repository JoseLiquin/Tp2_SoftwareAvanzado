package com.example.tp2.repository;

import com.example.tp2.model.ConfiguracionLimite;
import com.example.tp2.model.TipoUsuarioLimite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ConfiguracionLimiteRepository extends JpaRepository<ConfiguracionLimite, UUID> {
    Optional<ConfiguracionLimite> findByTipoUsuario(TipoUsuarioLimite tipoUsuario);
}