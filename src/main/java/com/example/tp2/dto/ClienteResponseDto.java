package com.example.tp2.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ClienteResponseDto {
    private UUID id;
    private String nombre;
    private String apellido;
    private String cuil;
    private String email;
}
