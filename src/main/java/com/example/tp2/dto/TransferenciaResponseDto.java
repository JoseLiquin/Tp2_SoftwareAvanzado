package com.example.tp2.dto;

import com.example.tp2.model.EstadoTransaccion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaResponseDto {
    private UUID nroComprobante;
    private String cbuOrigen;
    private String cbuDestino;
    private Double monto;
    private EstadoTransaccion estadoTransaccion;
    private Date fecha;
    private LocalTime hora;
}