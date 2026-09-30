package com.example.tp2.dto;

import com.example.tp2.model.EstadoCuenta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaResponseDto {
    private String cbu;
    private String alias;
    private Double saldo;
    private EstadoCuenta estado;
    private String titular;
    private String nombreTitular;
    private String cuilTitular;
}