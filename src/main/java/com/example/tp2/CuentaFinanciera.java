package com.example.tp2;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cuentas_financieras")
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class CuentaFinanciera {

    @Id
    private Long cbu; // Sin @GeneratedValue, el CBU se ingresa manualmente

    private String alias;

    protected double saldo;

    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_cuil")
    private Cliente cliente;

    @OneToMany(mappedBy = "cuenta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transaccion> transacciones = new ArrayList<>();

    // Constructores
    public CuentaFinanciera() {
    }

    public CuentaFinanciera(Long cbu, String alias, double saldo, String estado) {
        this.cbu = cbu;
        this.alias = alias;
        this.saldo = saldo;
        this.estado = estado;
    }

    public void depositar(double monto) {
        if (monto > 0) {
            this.saldo += monto;
            System.out.println("Depósito exitoso. Nuevo saldo: " + this.saldo);
        }
    }


    public abstract boolean extraer(double monto);

    public void enviarTransferencia(double monto, Long cbuDestino) {
        if (monto > this.saldo) {
            System.out.println("Saldo insuficiente para realizar la transferencia.");
            return;
        }
        this.saldo -= monto;
        System.out.println("Transferencia exitosa de " + monto + " al CBU: " + cbuDestino);
    }

    // Getters y Setters
    public Long getCbu() {
        return cbu;
    }

    public void setCbu(Long cbu) {
        this.cbu = cbu;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }
}