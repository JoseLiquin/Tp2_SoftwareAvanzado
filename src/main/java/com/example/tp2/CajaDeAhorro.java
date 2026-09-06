package com.example.tp2;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "cajas_ahorro")
@PrimaryKeyJoinColumn(name = "cbu")

public class CajaDeAhorro extends CuentaFinanciera {

    private int cupoLimite;
    private double interesAnual;
    private int extraccionesRealizadas;

    // Constructores
    public CajaDeAhorro() {
        super();
    }

    public CajaDeAhorro(Long cbu, String alias, double saldo, String estado, int cupoLimite, double interesAnual) {
        super(cbu, alias, saldo, estado);
        this.cupoLimite = cupoLimite;
        this.interesAnual = interesAnual;
        this.extraccionesRealizadas = 0;
    }

    @Override

    public boolean extraer(double monto) {
        if (this.saldo < monto) {
            System.out.println("Extracción rechazada: Saldo insuficiente.");
            return false;
        }
        if (this.extraccionesRealizadas >= this.cupoLimite) {
            System.out.println("Extracción rechazada: Ha alcanzado el límite de extracciones mensuales.");
            return false;
        }
        this.saldo -= monto;
        this.extraccionesRealizadas++;
        System.out.println("Extracción exitosa por: " + monto + ". Extracciones del mes: " + this.extraccionesRealizadas + "/" + this.cupoLimite);
        return true;
    }

    public void calcularInteres() {
        double montoInteres = this.saldo * (this.interesAnual / 100);
        this.saldo += montoInteres;
        System.out.println("Interés acreditado: " + montoInteres + ". Nuevo saldo: " + this.saldo);
    }

    public void reiniciarCupoMensual() {
        this.extraccionesRealizadas = 0;
        System.out.println("Se ha reiniciado el cupo de extracciones para el nuevo mes.");
    }

    // Getters y Setters
    public int getCupoLimite() {
        return cupoLimite;
    }

    public void setCupoLimite(int cupoLimite) {
        this.cupoLimite = cupoLimite;
    }

    public double getInteresAnual() {
        return interesAnual;
    }

    public void setInteresAnual(double interesAnual) {
        this.interesAnual = interesAnual;
    }

    public int getExtraccionesRealizadas() {
        return extraccionesRealizadas;
    }

    public void setExtraccionesRealizadas(int extraccionesRealizadas) {
        this.extraccionesRealizadas = extraccionesRealizadas;
    }
}