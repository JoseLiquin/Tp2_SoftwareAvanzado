package com.example.tp2.service;

import com.example.tp2.dto.ExtraccionRequestDto;
import com.example.tp2.dto.ExtraccionResponseDto;
import com.example.tp2.exception.LimiteExcedidoException;
import com.example.tp2.exception.RecursoNoEncontradoException;
import com.example.tp2.exception.SaldoInsuficienteException;
import com.example.tp2.model.AcumuladorExtraccion;
import com.example.tp2.model.CajaDeAhorro;
import com.example.tp2.model.ConfiguracionLimite;
import com.example.tp2.model.CuentaFinanciera;
import com.example.tp2.repository.AcumuladorExtraccionRepository;
import com.example.tp2.repository.ConfiguracionLimiteRepository;
import com.example.tp2.repository.CuentaFinancieraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CajaDeAhorroServiceImpl implements CajaDeAhorroService {

    private final CuentaFinancieraRepository cuentaRepository;
    private final ConfiguracionLimiteRepository configuracionLimiteRepository;
    private final AcumuladorExtraccionRepository acumuladoRepository;

    @Override
    @Transactional
    public ExtraccionResponseDto extraerConControlDeTope(ExtraccionRequestDto requestDto) {
        log.info("Validando extracción de {} para usuario {} ({}) en cuenta CBU {}",
                requestDto.getMonto(), requestDto.getUsuarioId(), requestDto.getTipoUsuario(), requestDto.getCbu());

        // 1. Buscar la cuenta y validar que sea Caja de Ahorro
        CuentaFinanciera cuentaGenerica = cuentaRepository.findByCbu(requestDto.getCbu())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una cuenta con CBU: " + requestDto.getCbu()));

        if (!(cuentaGenerica instanceof CajaDeAhorro cuenta)) {
            throw new IllegalArgumentException("El control de tope solo aplica a cuentas de Caja de Ahorro");
        }

        // 2. Buscar el límite configurado para ese tipo de usuario
        ConfiguracionLimite limite = configuracionLimiteRepository
                .findByTipoUsuario(requestDto.getTipoUsuario())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No hay configuración de límite para: " + requestDto.getTipoUsuario()));

        // 3. Buscar (o crear) el acumulado del día para ese usuario
        AcumuladorExtraccion acumulado = acumuladoRepository
                .findByUsuarioIdAndFecha(requestDto.getUsuarioId(), LocalDate.now())
                .orElse(AcumuladorExtraccion.builder()
                        .usuarioId(requestDto.getUsuarioId())
                        .tipoUsuario(requestDto.getTipoUsuario())
                        .fecha(LocalDate.now())
                        .montoAcumulado(BigDecimal.ZERO)
                        .build());

        // 4. Validar si se supera el tope diario
        BigDecimal totalSiSeExtrae = acumulado.getMontoAcumulado().add(requestDto.getMonto());
        if (totalSiSeExtrae.compareTo(limite.getMontoMaximoDiario()) > 0) {
            log.error("Límite excedido para usuario {}: acumulado {} + nuevo {} > tope {}",
                    requestDto.getUsuarioId(), acumulado.getMontoAcumulado(), requestDto.getMonto(), limite.getMontoMaximoDiario());
            throw new LimiteExcedidoException(
                    "El monto supera el límite diario de $" + limite.getMontoMaximoDiario()
                            + " para " + requestDto.getTipoUsuario());
        }

        // 5. Ejecutar la extracción real sobre la cuenta
        boolean exito = cuenta.extraer(requestDto.getMonto().doubleValue());
        if (!exito) {
            throw new SaldoInsuficienteException("Saldo insuficiente en la cuenta.");
        }
        cuentaRepository.save(cuenta);

        // 6. Actualizar el acumulado diario con el nuevo total
        acumulado.setMontoAcumulado(totalSiSeExtrae);
        acumuladoRepository.save(acumulado);

        log.info("Extracción autorizada y completada. Nuevo acumulado diario: {}", totalSiSeExtrae);

        return ExtraccionResponseDto.builder()
                .cbu(cuenta.getCbu())
                .montoExtraido(requestDto.getMonto())
                .saldoRestante(BigDecimal.valueOf(cuenta.getSaldo()))
                .acumuladoDiario(totalSiSeExtrae)
                .estado("COMPLETADA")
                .build();
    }
}