package com.example.tp2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "cuentas_corrientes")
@PrimaryKeyJoinColumn(name="id")

public class CuentaCorriente extends CuentaFinanciera{

    @Column(name= "limiteDescubierto",nullable=false,length=10)//columna de limite
    private double limiteDescubierto;  // Equivale al margen/descubierto autorizado

    @Column(name= "comisionMantenimiento",nullable=false,length=10)//columna de la comision
    private double comisionMantenimiento;

    public CuentaCorriente() {
    }

    public CuentaCorriente(String cbu, String alias, double saldo, EstadoCuenta estado, double comisionMantenimiento, double limiteDescubierto) {
        super(cbu, alias, saldo, estado);
        this.comisionMantenimiento = comisionMantenimiento;
        this.limiteDescubierto = limiteDescubierto;
    }

    //metodo de extraccion heredada de cuenta finaciera
    public boolean extraer(double monto) {
        if ((this.saldo + this.limiteDescubierto) < monto) {
            System.out.println("Extracción rechazada: Supera el límite de descubierto autorizado.");
            return false;
        }
        this.saldo -= monto;
        System.out.println("Extracción exitosa por: " + monto + ". Nuevo saldo: " + this.saldo);
        return true;
    }

    //metodo del cobro de comision mensual
    public void cobrarComision() {
        this.saldo -= this.comisionMantenimiento;
        System.out.println("Comisión de mantenimiento cobrada: " + this.comisionMantenimiento + ". Nuevo saldo: " + this.saldo);
    }

    // Getters y Setters

    public double getLimiteDescubierto() {
        return limiteDescubierto;
    }

    public void setLimiteDescubierto(double limiteDescubierto) {
        this.limiteDescubierto = limiteDescubierto;
    }

    public double getComisionMantenimiento() {
        return comisionMantenimiento;
    }

    public void setComisionMantenimiento(double comisionMantenimiento) {
        this.comisionMantenimiento = comisionMantenimiento;
    }
}