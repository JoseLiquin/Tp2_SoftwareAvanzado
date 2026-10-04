package com.example.tp2.controller;

import com.example.tp2.dto.CuentaRequestDto;
import com.example.tp2.dto.CuentaResponseDto;
import com.example.tp2.service.CuentaFinancieraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cuentas")
@RequiredArgsConstructor

public class CuentaController {

    private final CuentaFinancieraService cuentaService;
    //crear una cuenta asociada a un cliente
    @PostMapping
    public ResponseEntity<CuentaResponseDto> crearCuenta(@Valid @RequestBody CuentaRequestDto requestDto) {
        CuentaResponseDto response = cuentaService.crearCuenta(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //obtener por cbu
    @GetMapping("/{cbu}")
    public ResponseEntity<CuentaResponseDto> obtenerPorCbu(@PathVariable String cbu) {
        CuentaResponseDto response = cuentaService.obtenerPorCbu(cbu);
        return ResponseEntity.ok(response);
    }


    //METODOS APARTES
    //configurar para agregar cotitulares
    @PostMapping("/{cbu}/titulares")
    public ResponseEntity<Void> agregarTitular(@PathVariable String cbu, @RequestBody java.util.Map<String, UUID> request) {
        UUID clienteId = request.get("clienteId");
        cuentaService.agregarTitular(cbu, clienteId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}