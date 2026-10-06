package com.example.tp2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CajaDeAhorroResponseDto {
    private String cbu;
    private String alias;
    private Double saldo;
    private String titular;
    private Integer cupoLimite;
    private Double interesAnual;
}