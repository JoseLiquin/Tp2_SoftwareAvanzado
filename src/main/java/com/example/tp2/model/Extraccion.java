package com.example.tp2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "extracciones")
@Data
public class Extraccion {
    @Id
    private String clave;//a cual cuenta le corresponde
    private String valor; //valor de extraccion
}
