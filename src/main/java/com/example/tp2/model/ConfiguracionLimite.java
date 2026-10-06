package com.example.tp2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "configuracion_limites")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracionLimite {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TipoUsuarioLimite tipoUsuario;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal montoMaximoDiario;
}

