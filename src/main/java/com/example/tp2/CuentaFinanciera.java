package com.example.tp2;

public class CuentaFinanciera {
    private long CBU;
    private String alias;
    protected double saldo;
    private String estado;

    public CuentaFinanciera() {

    }

    public long getCBU() {
        return CBU;
    }

    public void setCBU(long CBU) {
        this.CBU = CBU;
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

    public CuentaFinanciera(int CBU, String alias, float saldo, String estado) {
        this.CBU = CBU;
        this.alias = alias;
        this.saldo = saldo;
        this.estado = estado;
    }
    public void depositar(double saldoActual){

        saldoActual =+ saldo;
    }
    public void extraer(double extraccion){

        if(saldo < extraccion){
            System.out.println("Saldo insufiente para extracion");
        }else {
            saldo = -extraccion;
        }

    }

    public void enviarTransferencia(double transferir, long cbu){
        if(transferir < saldo){
            System.out.println("Saldo insuficiente");
        }
        saldo =- transferir;
        System.out.println("Tranferencia exitosa a: "+ saldo);
    }
}
