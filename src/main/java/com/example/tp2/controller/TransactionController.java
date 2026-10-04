package com.example.tp2.controller;

import com.example.tp2.dto.TransferenciaRequestDto;
import com.example.tp2.dto.TransferenciaResponseDto;
import com.example.tp2.service.TransaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transacciones")
@RequiredArgsConstructor
public class TransactionController {

    private final TransaccionService transaccionService;
    //metodo de transferir
    @PostMapping("/transferir")
    public ResponseEntity<TransferenciaResponseDto> transferir(@Valid @RequestBody TransferenciaRequestDto requestDto) {
        TransferenciaResponseDto response = transaccionService.transferir(
                requestDto.getCbuOrigen(),
                requestDto.getCbuDestino(),
                requestDto.getMonto()
        );
        return ResponseEntity.ok(response);
    }
}