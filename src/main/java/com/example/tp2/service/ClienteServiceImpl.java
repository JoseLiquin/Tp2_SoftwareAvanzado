package com.example.tp2.service;

import com.example.tp2.model.Cliente;
import com.example.tp2.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService{

    private final ClienteRepository clienteRepository;

    @Override
    public Cliente crearCliente(Cliente cliente) {
        // TODO Auto-generated method stub
        log.info("Iniciando proceso de creación de cliente con CUIL: {}", cliente.getCuil());
        if (clienteRepository.existsByCuilOrEmail(cliente.getCuil(), cliente.getEmail())) {
            log.error("Fallo al crear cliente. Ya existe un registro con CUIL: {} o email: {}",
                    cliente.getCuil(), cliente.getEmail());
            throw new IllegalArgumentException("Ya existe un cliente registrado con ese CUIL o email");
        }
        Cliente clienteGuardado = clienteRepository.save(cliente);
        log.info("Cliente registrado con ID: {}", clienteGuardado.getId());

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
                    return new IllegalArgumentException("No existe un cliente con el CUIL: " + cuil);
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
    public Cliente actualizarCliente(UUID id,Cliente detalles) {
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
