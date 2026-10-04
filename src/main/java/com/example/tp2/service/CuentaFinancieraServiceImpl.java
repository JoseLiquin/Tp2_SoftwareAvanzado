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

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CuentaFinancieraServiceImpl implements CuentaFinancieraService {

    private final CuentaFinancieraRepository cuentaRepository;
    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    //crear una cuenta asociada al cliente
    public CuentaResponseDto crearCuenta(CuentaRequestDto requestDto) {
        log.info("Creando cuenta para el cliente ID: {}", requestDto.getId());

        //buscar el cliente titular en la base de datos
        Cliente cliente = clienteRepository.findById(requestDto.getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el cliente con ID: " + requestDto.getId()));

        // crearlo con con cuenta de ahorro
        CajaDeAhorro cuenta = new CajaDeAhorro();
        cuenta.setCbu(requestDto.getCbu());
        cuenta.setAlias(requestDto.getAlias());
        cuenta.setSaldo(0.0);
        cuenta.setEstado(EstadoCuenta.ACTIVA);
        //atributos de la caja de ahorro
        cuenta.setCupoLimite(5);
        cuenta.setInteresAnual(35.0);
        cuenta.setExtraccionesRealizadas(0);

        // asociar la relación
        cuenta.getTitulares().add(cliente);
        cliente.getCuentas().add(cuenta);

        // guardarlo en la bdd
        CuentaFinanciera cuentaGuardada = cuentaRepository.save(cuenta);
        log.info("Cuenta creada exitosamente con CBU: {}", cuentaGuardada.getCbu());

        return mapToResponseDto(cuentaGuardada);
    }
    //obtener un cliente apartir del cbu
    @Override
    @Transactional(readOnly = true)
    public CuentaResponseDto obtenerPorCbu(String cbu) {
        CuentaFinanciera cuenta = cuentaRepository.findByCbu(cbu)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encuentra una cuenta con ese CBU"));
        return mapToResponseDto(cuenta);
    }

    //METODOS APARTES
    //obtener cbu que nos devuelva una cuenta
    @Override
    @Transactional(readOnly = true)
    public CuentaFinanciera obtenerEntidadPorCbu(String cbu) {
        return cuentaRepository.findByCbu(cbu)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encuentra una cuenta con ese CBU"));
    }
    //mapear las response para allar titulares y cotitulares
    private CuentaResponseDto mapToResponseDto(CuentaFinanciera cuenta) {
        CuentaResponseDto.TitularDto titularPrincipal = null;
        List<CuentaResponseDto.TitularDto> cotitulares = new java.util.ArrayList<>();

        if (cuenta.getTitulares() != null && !cuenta.getTitulares().isEmpty()) {
            //el primer elemento sera el titular principal (el que creó la cuenta)
            Cliente clientePrincipal = cuenta.getTitulares().get(0);
            titularPrincipal = CuentaResponseDto.TitularDto.builder()
                    .nombreCompleto(clientePrincipal.getNombre() + " " + clientePrincipal.getApellido())
                    .cuil(clientePrincipal.getCuil())
                    .build();

            //el resto son cotitulares
            if (cuenta.getTitulares().size() > 1) {
                cotitulares = cuenta.getTitulares().stream()
                        .skip(1) // Omitimos al titular principal
                        .map(cliente -> CuentaResponseDto.TitularDto.builder()
                                .nombreCompleto(cliente.getNombre() + " " + cliente.getApellido())
                                .cuil(cliente.getCuil())
                                .build())
                        .toList();
            }
        }
        //que nos devuelva un response, con las lista correspondiente
        return CuentaResponseDto.builder()
                .cbu(cuenta.getCbu())
                .alias(cuenta.getAlias())
                .saldo(cuenta.getSaldo())
                .estado(cuenta.getEstado())
                .titularPrincipal(titularPrincipal)
                .cotitulares(cotitulares)
                .build();
    }
    //accion de agregar cotitulares
    @Transactional
    public void agregarTitular(String cbu, UUID clienteId) {
        // cargar la cuenta
        CuentaFinanciera cuenta = obtenerEntidadPorCbu(cbu);

        // por si no se encuentra al cliente
        Cliente nuevoTitular = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));

        //controlar que no se dupliquen
        if (!cuenta.getTitulares().contains(nuevoTitular)) {
            cuenta.getTitulares().add(nuevoTitular);
            nuevoTitular.getCuentas().add(cuenta);

            //se guarda en una tabla intermedia
            cuentaRepository.save(cuenta);
        }
    }
}