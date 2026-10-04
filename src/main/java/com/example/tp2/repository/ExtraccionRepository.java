package com.example.tp2.repository;

import com.example.tp2.model.Extraccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraccionRepository extends JpaRepository<Extraccion, String> {

}
