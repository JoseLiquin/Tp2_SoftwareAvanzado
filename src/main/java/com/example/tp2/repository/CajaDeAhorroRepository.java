package com.example.tp2.repository;

import com.example.tp2.model.CajaDeAhorro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CajaDeAhorroRepository extends JpaRepository<CajaDeAhorro, Long> {

}