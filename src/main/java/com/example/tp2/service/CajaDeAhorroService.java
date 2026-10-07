package com.example.tp2.service;

import com.example.tp2.dto.ExtraccionRequestDto;
import com.example.tp2.dto.ExtraccionResponseDto;

public interface CajaDeAhorroService {
    ExtraccionResponseDto extraerConControlDeTope(ExtraccionRequestDto requestDto);
}