package com.example.tp2.service;

import com.example.tp2.dto.CuentaRequestDto;
import com.example.tp2.dto.CuentaResponseDto;
import com.example.tp2.exception.RecursoNoEncontradoException;
import com.example.tp2.model.CajaDeAhorro;
import com.example.tp2.model.Cliente;
import com.example.tp2.model.CuentaFinanciera;
import com.example.tp2.model.EstadoCuenta;
import com.example.tp2.repository.ClienteRepository;
import com.example.tp2.repository.CuentaFinancieraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CuentaFinancieraServiceImpl implements CuentaFinancieraService {

    private final CuentaFinancieraRepository cuentaRepository;
    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public CuentaResponseDto crearCuenta(CuentaRequestDto requestDto) {
        log.info("Creando cuenta para el cliente ID: {}", requestDto.getId());

        // 1. Buscar el cliente titular en la base de datos
        Cliente cliente = clienteRepository.findById(requestDto.getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el cliente con ID: " + requestDto.getId()));

        // 2. Instanciar CajaDeAhorro (clase concreta)
        CajaDeAhorro cuenta = new CajaDeAhorro();
        cuenta.setCbu(requestDto.getCbu());
        cuenta.setAlias(requestDto.getAlias());
        cuenta.setSaldo(0.0);
        cuenta.setEstado(EstadoCuenta.ACTIVA);
        cuenta.setCupoLimite(5);
        cuenta.setInteresAnual(35.0);
        cuenta.setExtraccionesRealizadas(0);

        // 3. Asociar la relación bidireccional
        cuenta.getTitulares().add(cliente);
        cliente.getCuentas().add(cuenta);

        // 4. Guardar la cuenta en la base de datos
        CuentaFinanciera cuentaGuardada = cuentaRepository.save(cuenta);
        log.info("Cuenta creada exitosamente con CBU: {}", cuentaGuardada.getCbu());

        return mapToResponseDto(cuentaGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public CuentaResponseDto obtenerPorCbu(String cbu) {
        CuentaFinanciera cuenta = cuentaRepository.findByCbu(cbu)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encuentra una cuenta con ese CBU"));
        return mapToResponseDto(cuenta);
    }

    @Override
    @Transactional(readOnly = true)
    public CuentaFinanciera obtenerEntidadPorCbu(String cbu) {
        return cuentaRepository.findByCbu(cbu)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encuentra una cuenta con ese CBU"));
    }

    private CuentaResponseDto mapToResponseDto(CuentaFinanciera cuenta) {
        String nombre = null;
        String cuil = null;

        if (cuenta.getTitulares() != null && !cuenta.getTitulares().isEmpty()) {
            Cliente titular = cuenta.getTitulares().get(0);
            nombre = titular.getNombre() + " " + titular.getApellido();
            cuil = titular.getCuil();
        }

        return CuentaResponseDto.builder()
                .cbu(cuenta.getCbu())
                .alias(cuenta.getAlias())
                .saldo(cuenta.getSaldo())
                .estado(cuenta.getEstado())
                .titular(nombre)
                .nombreTitular(nombre)
                .cuilTitular(cuil)
                .build();
    }
}