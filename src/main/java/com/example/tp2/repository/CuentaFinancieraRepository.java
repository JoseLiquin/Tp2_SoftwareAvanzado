package com.example.tp2.repository;

import com.example.tp2.model.CuentaFinanciera;
import com.example.tp2.model.EstadoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CuentaFinancieraRepository extends JpaRepository<CuentaFinanciera, UUID> {

    // buscar todas las cuentas asociadas al CUIL de un cliente


    //buscar todas las cuentas asociadas por el cbu de un cliente
    Optional<CuentaFinanciera> findByCbu(String cbu);

    // buscar una cuenta por su Alias
    Optional<CuentaFinanciera> findByAlias(String alias);

    // buscar cuentas por estado (ACTIVA, SUSPENDIDA, BLOQUEADA)
    List<CuentaFinanciera> findByEstado(EstadoCuenta estado);

    // buscar cuentas asociadas al CUIL de un titular
    List<CuentaFinanciera> findByTitulares_Cuil(String cuil);;
}