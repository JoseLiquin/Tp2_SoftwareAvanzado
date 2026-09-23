package com.example.tp2.model;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name="cuentas_financieras")
@Inheritance(strategy=InheritanceType.JOINED)

public abstract class CuentaFinanciera extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) //columna del id
    private UUID id;

    @Column(name= "CBU",nullable=false,unique=true,length=20) //columna del cbu
    private String cbu;

    @Column(name= "alias",nullable=false,unique=true,length=15) //culumna del alias
    private String alias;

    @Column(name= "saldo",nullable=false,length=10) //columna del saldo
    protected double saldo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 15)
    private EstadoCuenta estado;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="cliente_cuil", insertable=false,updatable=false)
    private Cliente cliente;

    @OneToMany(mappedBy= "cuenta", fetch =FetchType.LAZY,cascade =CascadeType.ALL)
    private List<Transaccion> transacciones;

    public CuentaFinanciera() {

    }

//cambiar el constructor por el build
    public CuentaFinanciera(String cbu, String alias, double saldo, EstadoCuenta estado) {
        this.cbu = cbu;
        this.alias = alias;
        this.saldo = saldo;
        this.estado = estado;
    }
//operacion de depositar
    public void depositar(double monto) {
        if (monto > 0) {
            this.saldo += monto;
            System.out.println("Depósito exitoso. Nuevo saldo: " + this.saldo);
        }
    }

//operacion de extraer el cual utiliza la caja de ahorro y la cuenta corriente
    public abstract boolean extraer(double monto);

//operacion de enviar transferencia
    public void enviarTransferencia(double monto, Long cbuDestino) {
        if (monto > this.saldo) {
            System.out.println("Saldo insuficiente para realizar la transferencia.");
            return;
        }
        this.saldo -= monto;
        System.out.println("Transferencia exitosa de " + monto + " al CBU: " + cbuDestino);
    }

    // Getters y Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
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

    public EstadoCuenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoCuenta estado) {
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

    @Override
    public String toString() {
        return "CuentaFinanciera{" +
                ", alias='" + alias + '\'' +
                ", cbu='" + cbu + '\'' +
                ", saldo=" + saldo +
                ", clienteCuil=" + (cliente != null ? cliente.getCuil() : null) + //
                '}';
    }
}