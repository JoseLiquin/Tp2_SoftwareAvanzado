package com.example.tp2.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity

@Table(name="clientes")
public class Cliente extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) //id del cliente
    private UUID id;

    @Column(name = "nombre", nullable = false, length = 20) //columna de nombre del cliente
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 20) //columna de apellido del cliente
    private String apellido;

    @Column(name = "cuil", nullable = false, unique = true, length = 12) //columna del cuil del cliente
    private String cuil;

    @Column(name = "email", nullable = false, length = 100) //columna del email del cliente
    private String email;

    @Column(name = "telefono", nullable = false, length = 9) //columna del telefono del cliente
    private long telefono;

    @Column(name = "direccion", nullable = false, length = 50) //columna de la dirrecion del cliente
    private String direccion;

    @ManyToMany
    @JoinTable(
            name = "cuenta_cliente",
            joinColumns = @JoinColumn(name = "cuenta_id"),
            inverseJoinColumns = @JoinColumn(name = "cliente_id")
    )
    private List<Cliente> titulares; //buscar como represantar en la base de datos

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cliente_cuil")
    private List<CuentaFinanciera> cuentas;

    //cambiar constructores por builder del spring boot


    public Cliente(UUID id, String nombre, String apellido, String cuil, String email, long telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cuil = cuil;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Cliente() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Cliente> getTitulares() {
        return titulares;
    }

    public void setTitulares(List<Cliente> titulares) {
        this.titulares = titulares;
    }

    public List<CuentaFinanciera> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaFinanciera> cuentas) {
        this.cuentas = cuentas;
    }

    public void registrarCliente(String nombre) {
        System.out.println("se registro el cliente " + nombre);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cuil=" + cuil +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                //          ", cantidadCuentas=" + (cuentas != null ? cuentas.size() : 0) +
                '}';
    }
}
