package com.example.tp2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "cajas_ahorros")
@PrimaryKeyJoinColumn(name = "id")

public class CajaDeAhorro extends CuentaFinanciera {
    @Column(name= "cupoLimite",nullable=false,length=10) //columna cupo limite
    private int cupoLimite;

    @Column(name= "interesAnual",nullable=false,length=20)//columna interes anual
    private double interesAnual;

    @Column (name= "extraccionesRealizadas",nullable=false,length=1)//columna extraccion realizadas
    private int extraccionesRealizadas;

    // Constructores cambiar por el build
    public CajaDeAhorro() {
        super();
    }

    public CajaDeAhorro(String cbu, String alias, double saldo, EstadoCuenta estado, int cupoLimite, double interesAnual, int extraccionesRealizadas) {
        super(cbu, alias, saldo, estado);
        this.cupoLimite = cupoLimite;
        this.interesAnual = interesAnual;
        this.extraccionesRealizadas = extraccionesRealizadas;
    }

    @Override
//metodo de extraccion heredado de cuenta financiera
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

//metodo de calculo de interes
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