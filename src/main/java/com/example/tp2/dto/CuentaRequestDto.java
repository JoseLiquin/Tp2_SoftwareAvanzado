package com.example.tp2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaRequestDto {

    @NotNull(message = "El ID del cliente titular es obligatorio")
    private UUID id;

    @NotBlank(message = "El CBU es obligatorio")
    private String cbu;

    @NotBlank(message = "El alias es obligatorio")
    private String alias;
}