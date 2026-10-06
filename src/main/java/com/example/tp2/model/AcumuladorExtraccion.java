package com.example.tp2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "acumulador_extracciones")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcumuladorExtraccion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID usuarioId; // id del Cliente o del Adherente

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuarioLimite tipoUsuario;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal montoAcumulado;
}