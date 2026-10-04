package com.example.tp2.dto;

import com.example.tp2.model.EstadoCuenta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaResponseDto {
    private String cbu;
    private String alias;
    private Double saldo;
    private EstadoCuenta estado;

    // Titular principal (el primero)
    private TitularDto titularPrincipal;

    // Lista de cotitulares (del segundo en adelante)
    private List<TitularDto> cotitulares;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TitularDto {
        private String nombreCompleto;
        private String cuil;
    }
}