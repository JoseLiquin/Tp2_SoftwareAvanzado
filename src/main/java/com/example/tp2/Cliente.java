package com.example.tp2;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(
        name = "cliente"
)
public class Cliente {
    private String nombre;
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long cuil;
    private String email;
    private Long telefono;
    private String direccion;
    private String titularidad;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(
            name = "cliente_cuil"
    )
    private List<CuentaFinanciera> cuentas;

    public Cliente(String nombre, Long cuil, String email, Long telefono, String direccion) {
        this.nombre = nombre;
        this.cuil = cuil;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Cliente() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCuil() {
        return cuil;
    }

    public void setCuil(Long cuil) {
        this.cuil = cuil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTitularidad() {
        return titularidad;
    }

    public void setTitularidad(String titularidad) {
        this.titularidad = titularidad;
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
}
