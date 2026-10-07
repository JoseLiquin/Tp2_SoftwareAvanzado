package com.example.tp2.dto;


import com.example.tp2.model.TipoUsuarioLimite;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class ExtraccionRequestDto {

    @NotBlank(message = "El CBU de la cuenta es obligatorio")
    private String cbu;

    @NotNull(message = "El ID del usuario que opera es obligatorio")
    private UUID usuarioId; // id del Cliente (titular) o del Adherente

    @NotNull(message = "El tipo de usuario es obligatorio")
    private TipoUsuarioLimite tipoUsuario; // TITULAR o ADHERENTE

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a cero")
    private BigDecimal monto;
}

