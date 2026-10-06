package com.example.tp2.dto;

import com.example.tp2.model.ParentescoAdherente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdherenteResponseDto {
    private UUID id;
    private String nombre;
    private String cuil;
    private ParentescoAdherente parentesco;
    private String cuentaCbu;
}