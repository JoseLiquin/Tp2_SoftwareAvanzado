package com.example.tp2.service;

import com.example.tp2.dto.TransferenciaResponseDto;

public interface TransaccionService {

    TransferenciaResponseDto transferir(String cbuOrigen, String cbuDestino, Double monto);
}