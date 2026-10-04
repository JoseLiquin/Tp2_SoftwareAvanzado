package com.example.tp2.service;

import com.example.tp2.exception.RecursoNoEncontradoException;
import com.example.tp2.model.Cliente;
import com.example.tp2.model.EstadoCliente;
import com.example.tp2.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final EmailService emailService;

    @Override
    @Transactional
    public Cliente crearCliente(Cliente cliente) {
        if (clienteRepository.existsByCuilOrEmail(cliente.getCuil(), cliente.getEmail())) {
            throw new IllegalArgumentException("Ya existe un cliente registrado con ese CUIL o email");
        }

        cliente.setEstado(EstadoCliente.PENDIENTE_ACTIVACION);
        cliente.setTokenActivacion(UUID.randomUUID().toString());
        cliente.setTokenExpiracion(LocalDateTime.now().plusHours(24));

        Cliente clienteGuardado = clienteRepository.save(cliente);

        String linkActivacion = "http://localhost:8080/api/v1/clientes/activar?token=" + clienteGuardado.getTokenActivacion();

        String cuerpoHtml = """
            <div style="font-family: Arial, sans-serif; color: #333; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #dcdcdc; border-radius: 8px;">
                <h2 style="color: #004080; text-align: center;">Banco - Activación de Cuenta</h2>
                <p>Estimado/a <strong>%s</strong>,</p>
                <p>Gracias por registrarte. Haz clic en el siguiente botón para activar tu cuenta:</p>
                <div style="text-align: center; margin: 30px 0;">
                    <a href="%s" style="background-color: #004080; color: #ffffff; padding: 12px 24px; text-decoration: none; border-radius: 5px; font-weight: bold;">Activar Mi Cuenta</a>
                </div>
                <p style="font-size: 12px; color: #666;">Este enlace expirará en 24 horas.</p>
            </div>
        """.formatted(clienteGuardado.getNombre(), linkActivacion);

        // Llamada asíncrona al servicio de correo
        emailService.enviarCorreoHtmlAsincrono(clienteGuardado.getEmail(), "Confirma tu registro - Sistema Bancario", cuerpoHtml);

        return clienteGuardado;
    }

    @Override
    //@Transactional (readOnly=true)
    public Cliente obtenerPorId(UUID id) {
        log.debug("Buscando cliente por ID {}", id);

        return clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No se encontró ningún cliente con ID: {}", id);
                    return new IllegalArgumentException("No existe un cliente con el ID: " + id);
                });
    }

    @Override
//	@Transactional(readOnly=true)
    public Cliente obtenerPorCuil(String cuil) {
        log.debug("Buscando cliente por CUIL {}", cuil);

        return clienteRepository.findByCuil(cuil)
                .orElseThrow(() -> {
                    log.error("No se encontró ningún cliente con CUIL: {}", cuil);
                    return new RecursoNoEncontradoException("No existe un cliente con el CUIL: " + cuil);
                });
    }

    @Override
    @Transactional
    public Cliente obtenerPorNombre(String nombre) {
        log.debug("Buscar por nombre");

        return clienteRepository.findByNombre(nombre)
                .orElseThrow(() -> {
                    log.error("No se encuentra ningun Cliente");
                    return new RecursoNoEncontradoException("Nose encuentra al cliente");
                });
    }

    @Override
    //@Transactional(readOnly=true)
    public List<Cliente> listartodos() {
        log.debug("LISTADO DE LOS CLIENTES REGISTRADOS");
        return clienteRepository.findAll();
    }

    @Override
    @Transactional
    public Cliente actualizarCliente(UUID id, Cliente detalles) {
        log.info("Iniciando actualización de datos del cliente con ID {}", id);

        Cliente clienteExistente = obtenerPorId(id);
        clienteExistente.setTelefono(detalles.getTelefono());
        clienteExistente.setEmail(detalles.getEmail());

        return clienteRepository.save(clienteExistente);
    }


    @Override
    @Transactional
    public void eliminarPorId(UUID id) {
        // TODO Auto-generated method stub
        log.info("Solicitada la eliminación del cliente con ID {}", id);
        Cliente cliente = obtenerPorId(id);
        clienteRepository.delete(cliente);
        log.info("Cliente con ID {} eliminado correctamente", id);
    }
}
