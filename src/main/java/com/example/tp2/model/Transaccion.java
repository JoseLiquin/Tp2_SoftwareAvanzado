package com.example.tp2.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Temporal;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "transacciones")
public class Transaccion extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nroComprobante;
    @Column(nullable = false)
    private Date fecha;
    @Column(nullable = false)
    private LocalTime hora;
    private double monto;

    private String tipo;
    private String estadoTransaccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_cbu", nullable = false)
    private CuentaFinanciera cuenta;

    // Constructores
    public Transaccion() {
    }

    public Transaccion(Long nroComprobante, Date fecha, LocalTime hora, double monto, String tipo, String estadoTransaccion, CuentaFinanciera cuenta) {
        this.nroComprobante = nroComprobante;
        this.fecha = fecha;
        this.hora = hora;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
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
