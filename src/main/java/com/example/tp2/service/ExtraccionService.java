package com.example.tp2.service;

import com.example.tp2.model.*;
import com.example.tp2.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.descriptor.java.LocalTimeJavaType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExtraccionService {
    private final CuentaCorrienteRepository cuentaCorrienteRepository;
    private final CajaDeAhorroRepository cajaDeAhorroRepository;
    private final CuentaFinancieraRepository cuentaFinancieraRepository;
    private final TransaccionRepository transaccionRepository;
    private final ExtraccionRepository extraccionRepository;

    //@Scheduled(cron = "0 0 0 1 * ?")
    @Scheduled(fixedRate = 30000)
    @Transactional
    public void liquidarComision(){
        //empezamos con obtener los valos de la BdD
        log.info(("Inicio de proceso de liquidacion mensual..."));

        BigDecimal comisionCajaAhorro = new BigDecimal(
                extraccionRepository.findById("COMISION_CAJA_AHORRO")
                        .map(Extraccion::getValor)
                        .orElse("2000.00")
        );
        BigDecimal comisionCuentaCorriente = new BigDecimal(
                extraccionRepository.findById("COMISION_CAJA_AHORRO")
                        .map(Extraccion::getValor)
                        .orElse("2000.00")
        );

        //buscar las cuentas activas

        int pagina = 0;
        int tamañoLote = 100;
        Slice<CuentaFinanciera> slice;
        int totalProcesado = 0;

        do{
            Pageable pageable = PageRequest.of(pagina, tamañoLote);
            slice = cuentaFinancieraRepository.findByEstado(EstadoCuenta.ACTIVA, pageable);
            for(CuentaFinanciera cuenta : slice.getContent()){
                BigDecimal montoComision;

                //controlar el tipo de cuenta correspondiente
                if(cuenta instanceof CajaDeAhorro){
                    montoComision = comisionCajaAhorro;
                }else if(cuenta instanceof CuentaCorriente){
                    montoComision = comisionCuentaCorriente;
                }else{
                    continue;
                }

                //descontar saldos y guardarlo
                cuenta.setSaldo(cuenta.getSaldo()-montoComision.doubleValue());
                cuentaFinancieraRepository.save(cuenta);

                Transaccion transaccion = new Transaccion();
                transaccion.setCuenta(cuenta);
                transaccion.setMonto(montoComision.doubleValue());
                transaccion.setTipo(TipoTrasnsaccion.DEBITO_COMISION);
                transaccion.setEstadoTransaccion(EstadoTransaccion.COMPLETADA);
                transaccion.setFecha(new Date());
                transaccion.setHora(LocalTime.now());

                transaccionRepository.save(transaccion);
                totalProcesado++;
            }
        }while(slice.hasNext());

        log.info("Liquidación de comisiones finalizada con éxito. Total de cuentas procesadas: {}", totalProcesado);
    }
}
