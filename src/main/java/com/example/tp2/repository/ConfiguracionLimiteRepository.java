package com.example.tp2.repository;

import com.example.tp2.model.ConfiguracionLimite;

import com.example.tp2.model.CuentaCorriente;
import com.example.tp2.model.CuentaFinanciera;
import com.example.tp2.model.TipoUsuarioLimite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConfiguracionLimiteRepository extends JpaRepository<ConfiguracionLimite, UUID> {

    // Buscar cuentas corrientes con un límite de descubierto mayor al indicado
  

    Optional<CuentaFinanciera> findByTipoUsuario(TipoUsuarioLimite tipoUsuario);
}
