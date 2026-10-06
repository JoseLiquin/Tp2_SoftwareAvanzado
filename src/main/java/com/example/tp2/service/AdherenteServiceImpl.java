package com.example.tp2.service;

import com.example.tp2.dto.AdherenteRequestDto;
import com.example.tp2.dto.AdherenteResponseDto;
import com.example.tp2.exception.RecursoNoEncontradoException;
import com.example.tp2.model.Adherente;
import com.example.tp2.model.CajaDeAhorro;
import com.example.tp2.model.CuentaFinanciera;
import com.example.tp2.repository.AdherenteRepository;
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
public class AdherenteServiceImpl implements AdherenteService {

    private final AdherenteRepository adherenteRepository;
    private final CuentaFinancieraRepository cuentaRepository;

    @Override
    @Transactional
    public AdherenteResponseDto crearAdherente(AdherenteRequestDto requestDto) {
        log.info("Registrando adherente {} para la cuenta CBU: {}", requestDto.getNombre(), requestDto.getCuentaCbu());

        CuentaFinanciera cuenta = cuentaRepository.findByCbu(requestDto.getCuentaCbu())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una cuenta con CBU: " + requestDto.getCuentaCbu()));

        if (!(cuenta instanceof CajaDeAhorro cajaDeAhorro)) {
            throw new IllegalArgumentException("Solo se pueden vincular adherentes a cuentas de Caja de Ahorro");
        }

        Adherente adherente = Adherente.builder()
                .nombre(requestDto.getNombre())
                .cuil(requestDto.getCuil())
                .parentesco(requestDto.getParentesco())
                .cuenta(cajaDeAhorro)
                .build();

        Adherente guardado = adherenteRepository.save(adherente);
        log.info("Adherente {} vinculado correctamente a la cuenta {}", guardado.getNombre(), requestDto.getCuentaCbu());

        return mapearAResponseDto(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdherenteResponseDto> listarPorCuenta(String cbu) {
        CuentaFinanciera cuenta = cuentaRepository.findByCbu(cbu)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe una cuenta con CBU: " + cbu));

        return adherenteRepository.findByCuentaId(cuenta.getId()).stream()
                .map(this::mapearAResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public void eliminarAdherente(UUID adherenteId) {
        Adherente adherente = adherenteRepository.findById(adherenteId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un adherente con ID: " + adherenteId));

        adherenteRepository.delete(adherente);
        log.info("Adherente {} eliminado correctamente", adherenteId);
    }

    private AdherenteResponseDto mapearAResponseDto(Adherente adherente) {
        return AdherenteResponseDto.builder()
                .id(adherente.getId())
                .nombre(adherente.getNombre())
                .cuil(adherente.getCuil())
                .parentesco(adherente.getParentesco())
                .cuentaCbu(adherente.getCuenta().getCbu())
                .build();
    }
}