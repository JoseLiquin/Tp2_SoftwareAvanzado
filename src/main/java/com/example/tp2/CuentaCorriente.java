package com.example.tp2;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "cuentas_corrientes")
@PrimaryKeyJoinColumn(name = "cbu")
public class CuentaCorriente extends CuentaFinanciera {

    private double limiteDescubierto;      // Equivale al margen/descubierto autorizado
    private double comisionMantenimiento;  // Costo o comisión asignada

    // Constructores
    public CuentaCorriente() {
        super();
    }

    public CuentaCorriente(Long cbu, String alias, double saldo, String estado, double limiteDescubierto, double comisionMantenimiento) {
        super(cbu, alias, saldo, estado);
        this.limiteDescubierto = limiteDescubierto;
        this.comisionMantenimiento = comisionMantenimiento;
    }

    @Override
    public boolean extraer(double monto) {
        if ((this.saldo + this.limiteDescubierto) < monto) {
            System.out.println("Extracción rechazada: Supera el límite de descubierto autorizado.");
            return false;
        }
        this.saldo -= monto;
        System.out.println("Extracción exitosa por: " + monto + ". Nuevo saldo: " + this.saldo);
        return true;
    }

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