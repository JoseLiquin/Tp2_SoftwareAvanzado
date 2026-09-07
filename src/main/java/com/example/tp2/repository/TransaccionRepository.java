package com.example.tp2.repository;

import com.example.tp2.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    // Obtener el historial de transacciones de una cuenta por su CBU
    List<Transaccion> findByCuentaCbu(Long cbu);
}