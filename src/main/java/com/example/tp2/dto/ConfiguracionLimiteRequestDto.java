package com.example.tp2.dto;

import com.example.tp2.model.TipoUsuarioLimite;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracionLimiteRequestDto {

    @NotNull(message = "El tipo de usuario es obligatorio")
    private TipoUsuarioLimite tipoUsuario; // TITULAR o ADHERENTE

    @NotNull(message = "El monto máximo diario es obligatorio")
    @Positive(message = "El monto máximo diario debe ser mayor a cero")
    private BigDecimal montoMaximoDiario;
}