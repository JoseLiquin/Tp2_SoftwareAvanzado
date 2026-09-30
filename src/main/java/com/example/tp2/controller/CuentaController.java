package com.example.tp2.controller;

import com.example.tp2.dto.CuentaRequestDto;
import com.example.tp2.dto.CuentaResponseDto;
import com.example.tp2.service.CuentaFinancieraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaFinancieraService cuentaService;

    @PostMapping
    public ResponseEntity<CuentaResponseDto> crearCuenta(@Valid @RequestBody CuentaRequestDto requestDto) {
        CuentaResponseDto response = cuentaService.crearCuenta(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{cbu}")
    public ResponseEntity<CuentaResponseDto> obtenerPorCbu(@PathVariable String cbu) {
        CuentaResponseDto response = cuentaService.obtenerPorCbu(cbu);
        return ResponseEntity.ok(response);
    }
}