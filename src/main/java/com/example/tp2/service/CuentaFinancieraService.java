package com.example.tp2.service;

import com.example.tp2.model.CuentaFinanciera;

public interface CuentaFinancieraService {
    CuentaFinanciera crearCuenta(CuentaFinanciera cuenta);

    CuentaFinanciera obtenerPorCbu(String cbu);
}
