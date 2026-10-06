package com.example.tp2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClienteRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El cuil es obligatorio")
    private String cuil;

    @Email(message = "Formato invalido")
    @NotBlank(message = "El mail es obligatorio")
    private String email;

    private Long telefono;

    private String direccion;

}
