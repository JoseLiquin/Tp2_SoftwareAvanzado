
package com.example.tp2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtraccionResponseDto {
    private String cbu;
    private BigDecimal montoExtraido;
    private BigDecimal saldoRestante;
    private BigDecimal acumuladoDiario;
    private String estado; // "COMPLETADA"
}
