package com.example.tp2.model;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="transacciones")
public class Transaccion extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) //columna del nruo de comprobante
    private UUID nroComprobante;

    @Column(nullable = false)//columna de la fecha
    private Date fecha;

    @Column(nullable = false)//columna de la hora
    private LocalTime hora;

    @Column(name = "monto", nullable = false)//columna del monto
    private double monto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)//columna del tipo
    private TipoTrasnsaccion tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTransaccion estadoTransaccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_cbu", nullable = false)
    private CuentaFinanciera cuenta;



// Constructores
    public Transaccion() {
    }

    public Transaccion(UUID nroComprobante, Date fecha, LocalTime hora, double monto, TipoTrasnsaccion tipo, EstadoTransaccion estadoTransaccion, CuentaFinanciera cuenta) {
        this.nroComprobante = nroComprobante;
        this.fecha = fecha;
        this.hora = hora;
        this.monto = monto;
        this.tipo = tipo;
        this.estadoTransaccion = estadoTransaccion;
        this.cuenta = cuenta;
    }

// Getters y Setters


    public UUID getNroComprobante() {
        return nroComprobante;
    }

    public void setNroComprobante(UUID nroComprobante) {
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

    public TipoTrasnsaccion getTipo() {
        return tipo;
    }

    public void setTipo(TipoTrasnsaccion tipo) {
        this.tipo = tipo;
    }

    public EstadoTransaccion getEstadoTransaccion() {
        return estadoTransaccion;
    }

    public void setEstadoTransaccion(EstadoTransaccion estadoTransaccion) {
        this.estadoTransaccion = estadoTransaccion;
    }

    public CuentaFinanciera getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentaFinanciera cuenta) {
        this.cuenta = cuenta;
    }
}
