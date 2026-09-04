package com.example.tp2;

public class Cliente {

    private String nombre;
    private int cuil;
    private String email;
    private int telefono;
    private String direccion;

    public Cliente(String nombre, int cuil, String email, int telefono, String direccion) {
        this.nombre = nombre;
        this.cuil = cuil;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }
}
