package com.example.tp2.service;

import com.example.tp2.dto.ConfiguracionLimiteRequestDto;
import com.example.tp2.dto.ConfiguracionLimiteResponseDto;
import com.example.tp2.model.ConfiguracionLimite;
import com.example.tp2.repository.ConfiguracionLimiteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfiguracionLimiteServiceImpl implements ConfiguracionLimiteService {

    private final ConfiguracionLimiteRepository repository;

    @Override
    @Transactional
    public ConfiguracionLimiteResponseDto guardar(ConfiguracionLimiteRequestDto dto) {
        ConfiguracionLimite config = repository.findByTipoUsuario(dto.getTipoUsuario())
                .orElseGet(() -> ConfiguracionLimite.builder()
                        .tipoUsuario(dto.getTipoUsuario())
                        .build());

        config.setMontoMaximoDiario(dto.getMontoMaximoDiario());
        ConfiguracionLimite guardada = repository.save(config);

        log.info("Límite diario para {} fijado en {}", guardada.getTipoUsuario(), guardada.getMontoMaximoDiario());
        return mapear(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConfiguracionLimiteResponseDto> listar() {
        return repository.findAll().stream()
                .map(this::mapear)
                .toList();
    }

    private ConfiguracionLimiteResponseDto mapear(ConfiguracionLimite c) {
        return ConfiguracionLimiteResponseDto.builder()
                .id(c.getId())
                .tipoUsuario(c.getTipoUsuario())
                .montoMaximoDiario(c.getMontoMaximoDiario())
                .build();
    }
}