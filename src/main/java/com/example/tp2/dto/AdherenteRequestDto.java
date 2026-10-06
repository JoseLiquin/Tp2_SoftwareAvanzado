package com.example.tp2.dto;

import com.example.tp2.model.ParentescoAdherente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdherenteRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El CUIL es obligatorio")
    private String cuil;

    @NotNull(message = "El parentesco es obligatorio")
    private ParentescoAdherente parentesco;

    @NotBlank(message = "El CBU de la cuenta es obligatorio")
    private String cuentaCbu;
}