package com.example.tp2.repository;

import com.example.tp2.model.CuentaCorriente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CuentaCorrienteRepository extends JpaRepository<CuentaCorriente, UUID> {

    // Buscar cuentas corrientes con un límite de descubierto mayor al indicado
    List<CuentaCorriente> findByLimiteDescubiertoGreaterThan(double limiteDescubierto);

    // Buscar cuentas corrientes por comisión de mantenimiento exacta
    List<CuentaCorriente> findByComisionMantenimiento(double comisionMantenimiento);
}