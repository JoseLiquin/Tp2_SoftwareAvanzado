package com.example.tp2.controller;

import com.example.tp2.model.Extraccion;
import com.example.tp2.service.ExtraccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comision")
@RequiredArgsConstructor
public class ComisionController {

    private final ExtraccionService extraccionService;

    @PostMapping("/probar-comisiones")
    public ResponseEntity<String> probarLiquidacion() {
        // Ejecuta la liquidación de forma manual al hacer la petición desde Postman
        extraccionService.liquidarComision();
        return ResponseEntity.ok("¡Proceso de liquidación ejecutado manualmente con éxito!");
    }
}