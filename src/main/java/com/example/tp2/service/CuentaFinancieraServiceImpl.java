package com.example.tp2.service;

import com.example.tp2.exception.RecursoNoEncontradoException;
import com.example.tp2.model.CuentaFinanciera;
import com.example.tp2.repository.CuentaFinancieraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class CuentaFinancieraServiceImpl implements CuentaFinancieraService {

    private final CuentaFinancieraRepository cuentaRepository;

    @Override
    public CuentaFinanciera crearCuenta(CuentaFinanciera cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    public CuentaFinanciera obtenerPorCbu(String cbu) {
        return cuentaRepository.findByCbu(cbu)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encuentra una cuenta con ese cbu"));
    }
}
