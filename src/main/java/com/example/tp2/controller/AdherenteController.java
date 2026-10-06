package com.example.tp2.controller;

import com.example.tp2.dto.AdherenteRequestDto;
import com.example.tp2.dto.AdherenteResponseDto;
import com.example.tp2.service.AdherenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/adherentes")
@RequiredArgsConstructor
public class AdherenteController {

    private final AdherenteService adherenteService;

    @PostMapping
    public ResponseEntity<AdherenteResponseDto> crear(@Valid @RequestBody AdherenteRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adherenteService.crearAdherente(requestDto));
    }

    @GetMapping("/cuenta/{cbu}")
    public ResponseEntity<List<AdherenteResponseDto>> listarPorCuenta(@PathVariable String cbu) {
        return ResponseEntity.ok(adherenteService.listarPorCuenta(cbu));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        adherenteService.eliminarAdherente(id);
        return ResponseEntity.noContent().build();
    }
}