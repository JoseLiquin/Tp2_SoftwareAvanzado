package com.example.tp2.service;

import com.example.tp2.dto.CuentaRequestDto;
import com.example.tp2.dto.CuentaResponseDto;
import com.example.tp2.model.CuentaFinanciera;

import java.util.UUID;

public interface CuentaFinancieraService {
    CuentaResponseDto crearCuenta(CuentaRequestDto requestDto);
    CuentaResponseDto obtenerPorCbu(String cbu);

    CuentaFinanciera obtenerEntidadPorCbu(String cbu);
    void agregarTitular(String cbu, UUID clienteId);
}