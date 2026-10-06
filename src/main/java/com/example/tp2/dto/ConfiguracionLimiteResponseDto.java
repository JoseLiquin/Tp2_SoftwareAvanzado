package com.example.tp2.dto;

import com.example.tp2.model.TipoUsuarioLimite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracionLimiteResponseDto {
    private UUID id;
    private TipoUsuarioLimite tipoUsuario;
    private BigDecimal montoMaximoDiario;
}