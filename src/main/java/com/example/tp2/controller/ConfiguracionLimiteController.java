package com.example.tp2.controller;

import com.example.tp2.dto.ConfiguracionLimiteRequestDto;
import com.example.tp2.dto.ConfiguracionLimiteResponseDto;
import com.example.tp2.service.ConfiguracionLimiteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/configuracion-limites")
@RequiredArgsConstructor
public class ConfiguracionLimiteController {

    private final ConfiguracionLimiteService configuracionLimiteService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ConfiguracionLimiteResponseDto> guardar(@Valid @RequestBody ConfiguracionLimiteRequestDto dto) {
        return ResponseEntity.ok(configuracionLimiteService.guardar(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ConfiguracionLimiteResponseDto>> listar() {
        return ResponseEntity.ok(configuracionLimiteService.listar());
    }
}