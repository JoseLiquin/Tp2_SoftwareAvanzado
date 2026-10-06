package com.example.tp2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CajaDeAhorroRequestDto {

    @NotNull(message = "El ID del cliente titular es obligatorio")
    private UUID clienteId;

    @NotNull(message = "El cupo límite es obligatorio")
    @Positive(message = "El cupo límite debe ser mayor a cero")
    private Integer cupoLimite;

    @NotNull(message = "La tasa de interés anual es obligatoria")
    @Positive(message = "La tasa de interés anual debe ser mayor a cero")
    private Double interesAnual;
}