package com.example.tp2.controller;

import com.example.tp2.dto.ExtraccionRequestDto;
import com.example.tp2.dto.ExtraccionResponseDto;
import com.example.tp2.service.CuentaAhorroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cuentas-ahorro")
@RequiredArgsConstructor
public class CuentaAhorroController {

    private final CuentaAhorroService cuentaAhorroService;

    @PostMapping("/extraer")
    public ResponseEntity<ExtraccionResponseDto> extraer(@Valid @RequestBody ExtraccionRequestDto requestDto) {
        return ResponseEntity.ok(cuentaAhorroService.extraerConControlDeTope(requestDto));
    }
}