package com.example.tp2.service;

import com.example.tp2.dto.TransferenciaResponseDto;
import com.example.tp2.exception.SaldoInsuficienteException;
import com.example.tp2.model.CuentaFinanciera;
import com.example.tp2.model.EstadoTransaccion;
import com.example.tp2.model.TipoTrasnsaccion;
import com.example.tp2.model.Transaccion;
import com.example.tp2.repository.CuentaFinancieraRepository;
import com.example.tp2.repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacicionServiceImpl implements TransaccionService {

    private final CuentaFinancieraService cuentaService;
    private final CuentaFinancieraRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;

    @Override
    @Transactional
    public TransferenciaResponseDto transferir(String cbuOrigen, String cbuDestino, Double monto) {
        log.info("Iniciando transferencia de {} desde CBU: {} hacia CBU: {}", monto, cbuOrigen, cbuDestino);

        // Obtenemos las entidades usando el servicio
        CuentaFinanciera origen = cuentaService.obtenerEntidadPorCbu(cbuOrigen);
        CuentaFinanciera destino = cuentaService.obtenerEntidadPorCbu(cbuDestino);

        boolean extraccionExitosa = origen.extraer(monto);
        if (!extraccionExitosa) {
            log.error("Fallo en la transferencia: Saldo insuficiente o límite excedido para la cuenta {}", cbuOrigen);
            throw new SaldoInsuficienteException("Saldo insuficiente o límite de extracción superado para realizar la transferencia.");
        }

        destino.depositar(monto);

        cuentaRepository.save(origen);
        cuentaRepository.save(destino);

        Transaccion transaccionEnviada = new Transaccion();
        transaccionEnviada.setFecha(new Date());
        transaccionEnviada.setHora(LocalTime.now());
        transaccionEnviada.setMonto(monto);
        transaccionEnviada.setTipo(TipoTrasnsaccion.TRANSFERENCIA_ENVIADA);
        transaccionEnviada.setEstadoTransaccion(EstadoTransaccion.COMPLETADA);
        transaccionEnviada.setCuenta(origen);
        transaccionRepository.save(transaccionEnviada);

        Transaccion transaccionRecibida = new Transaccion();
        transaccionRecibida.setFecha(new Date());
        transaccionRecibida.setHora(LocalTime.now());
        transaccionRecibida.setMonto(monto);
        transaccionRecibida.setTipo(TipoTrasnsaccion.TRANSFERENCIA_RECIBIDA);
        transaccionRecibida.setEstadoTransaccion(EstadoTransaccion.COMPLETADA);
        transaccionRecibida.setCuenta(destino);
        transaccionRepository.save(transaccionRecibida);

        log.info("Transferencia completada exitosamente entre CBU {} y CBU {}", cbuOrigen, cbuDestino);

        return TransferenciaResponseDto.builder()
                .nroComprobante(transaccionEnviada.getNroComprobante() != null ? transaccionEnviada.getNroComprobante() : UUID.randomUUID())
                .cbuOrigen(cbuOrigen)
                .cbuDestino(cbuDestino)
                .monto(monto)
                .estadoTransaccion(EstadoTransaccion.COMPLETADA)
                .fecha(transaccionEnviada.getFecha())
                .hora(transaccionEnviada.getHora())
                .build();
    }
}