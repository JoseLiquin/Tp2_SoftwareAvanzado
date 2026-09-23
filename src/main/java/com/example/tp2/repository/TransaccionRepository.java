package com.example.tp2.repository;

import com.example.tp2.model.EstadoTransaccion;
import com.example.tp2.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, UUID> {

    // obtener el historial de transacciones de una cuenta por su CBU
    List<Transaccion> findByCuentaCbu(Long cbu);

    // Buscar transacciones por estado de procesamiento
    List<Transaccion> findByEstadoTransaccion(EstadoTransaccion estadoTransaccion);
}