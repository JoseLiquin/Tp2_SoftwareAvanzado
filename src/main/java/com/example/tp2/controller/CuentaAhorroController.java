package com.example.tp2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cuentas-ahorro")
@RequiredArgsConstructor
public class CuentaAhorroController {

    private final CuentaAhorroService cuentaAhorroService;

    // Extracción de titular o adherente con control de tope diario
    @PostMapping("/extracciones")
    public ResponseEntity<ExtraccionResponseDto> extraer(@Valid @RequestBody ExtraccionRequestDto dto) {
        return ResponseEntity.ok(cuentaAhorroService.extraerConControlDeTope(dto));
    }
}