package com.example.tp2.service;

/**
 * ConfiguracionLimiteService
 */
import com.example.tp2.dto.ConfiguracionLimiteRequestDto;
import com.example.tp2.dto.ConfiguracionLimiteResponseDto;
import java.util.List;

public interface ConfiguracionLimiteService {
    ConfiguracionLimiteResponseDto guardar(ConfiguracionLimiteRequestDto dto);
    List<ConfiguracionLimiteResponseDto> listar();
}