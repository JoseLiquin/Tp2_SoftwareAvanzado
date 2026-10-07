package com.example.tp2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "adherentes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Adherente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String cuil;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParentescoAdherente parentesco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private CajaDeAhorro cuenta;
}

