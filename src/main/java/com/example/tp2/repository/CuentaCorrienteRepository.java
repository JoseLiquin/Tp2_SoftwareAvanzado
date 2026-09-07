package com.example.tp2.repository;

import com.example.tp2.model.CuentaCorriente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaCorrienteRepository extends JpaRepository<CuentaCorriente, Long> {

}