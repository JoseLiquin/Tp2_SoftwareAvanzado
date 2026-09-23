package com.example.tp2.repository;

import com.example.tp2.model.CajaDeAhorro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CajaDeAhorroRepository extends JpaRepository<CajaDeAhorro, UUID> {

    // Buscar cajas de ahorro cuyo cupo límite de extracciones sea mayor al indicado
    List<CajaDeAhorro> findByCupoLimiteGreaterThan(int cupoLimite);

    // Buscar cajas de ahorro por tasa de interés anual exacta
    List<CajaDeAhorro> findByInteresAnual(double interesAnual);
}