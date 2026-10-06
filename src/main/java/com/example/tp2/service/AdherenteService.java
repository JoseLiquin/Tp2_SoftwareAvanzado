package com.example.tp2.service;

import com.example.tp2.dto.AdherenteRequestDto;
import com.example.tp2.dto.AdherenteResponseDto;

import java.util.List;
import java.util.UUID;

public interface AdherenteService {

    AdherenteResponseDto crearAdherente(AdherenteRequestDto requestDto);

    List<AdherenteResponseDto> listarPorCuenta(String cbu);

    void eliminarAdherente(UUID adherenteId);
}