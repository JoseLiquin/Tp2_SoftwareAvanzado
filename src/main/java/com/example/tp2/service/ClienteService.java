package com.example.tp2.service;

import com.example.tp2.model.Cliente;

import java.util.List;
import java.util.UUID;

//model VER CLASE DE PRACTICA

public interface ClienteService {
    Cliente crearCliente(Cliente cliente);
    Cliente obtenerPorId(UUID id);
    Cliente obtenerPorCuil(String cuil);
    List <Cliente> listartodos();
    Cliente actualizarCliente(UUID id,Cliente detalles);
    void eliminarPorId(UUID id);
}
