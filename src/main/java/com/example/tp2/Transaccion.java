package com.example.tp2;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones")
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nroComprobante;

    private LocalDateTime fechaHora;
    private double monto;
    private String tipo;
    private String estadoTransaccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_cbu", nullable = false)
    private CuentaFinanciera cuenta;

    // Constructores
    public Transaccion() {
    }

    public Transaccion(LocalDateTime fechaHora, double monto, String tipo, String estadoTransaccion, CuentaFinanciera cuenta) {
        this.fechaHora = fechaHora;
        this.monto = monto;
        this.tipo = tipo;
        this.estadoTransaccion = estadoTransaccion;
        this.cuenta = cuenta;
    }

    // Getters y Setters
    public Long getNroComprobante() {
        return nroComprobante;
    }

    public void setNroComprobante(Long nroComprobante) {
        this.nroComprobante = nroComprobante;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstadoTransaccion() {
        return estadoTransaccion;
    }

    public void setEstadoTransaccion(String estadoTransaccion) {
        this.estadoTransaccion = estadoTransaccion;
    }

    public CuentaFinanciera getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentaFinanciera cuenta) {
        this.cuenta = cuenta;
    }
}
