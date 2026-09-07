package com.example.tp2.repository;

import com.example.tp2.model.CuentaFinanciera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaFinancieraRepository extends JpaRepository<CuentaFinanciera, Long> {

    // Buscar todas las cuentas asociadas al CUIL de un cliente
    List<CuentaFinanciera> findByClienteCuil(Long cuil);

    // Buscar una cuenta por su Alias
    Optional<CuentaFinanciera> findByAlias(String alias);
}