package com.example.tp2;

public class Cajadeahorro extends CuentaFinanciera {
    private double interes;
    private double cupoLimite;

    public Cajadeahorro(double interes, double cupoLimite) {
        this.interes = interes;
        this.cupoLimite = cupoLimite;
    }

    public Cajadeahorro(int CBU, String alias, float saldo, String estado, double interes, double cupoLimite) {
        super(CBU, alias, saldo, estado);
        this.interes = interes;
        this.cupoLimite = cupoLimite;
    }
}

