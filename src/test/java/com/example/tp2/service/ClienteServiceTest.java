package com.example.tp2.service;

import com.example.tp2.model.Cliente;
import com.example.tp2.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;
    private UUID clienteID;

    @BeforeEach
    void setUp(){
        clienteID = UUID.randomUUID();
        cliente = new Cliente(clienteID, "Juan", "Perez", "2012345679", "juanperez@gmail.com", 3885123456L, "Av. lavalle 123");

    }

    @Test
    @DisplayName("Crear  un cliente cuando no existe ni cuil ni email")
    //Crear un cliente
    void crearCliente(){
        //verifica si esta o no
        when(clienteRepository.existsByCuilOrEmail(cliente.getCuil(), cliente.getEmail())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        //ejecutar
        Cliente resultado = clienteService.crearCliente(cliente);

        //verificacion final
        assertNotNull(resultado);
        assertEquals(clienteID, resultado.getId());
        assertEquals("2012345679", resultado.getCuil());

        verify(clienteRepository, times(1)).existsByCuilOrEmail(cliente.getCuil(), cliente.getEmail());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    @DisplayName("Retornar un cliente si esta su id")

    void obtenerId(){
        when(clienteRepository.findById(clienteID)).thenReturn(Optional.of(cliente));

        Cliente resultado = clienteService.obtenerPorId(clienteID);

        assertNotNull(resultado);
        assertEquals(clienteID, resultado.getId());
        verify(clienteRepository, times(1)).findById(clienteID);
    }

    @Test
    @DisplayName("Actualizar los datos del Cliente")
    void actualizarCliente(){
        Cliente datosNuevo = new Cliente();
        datosNuevo.setTelefono(3884802489L);
        datosNuevo.setEmail("juanperez123@gmail.com");

        when(clienteRepository.findById(clienteID)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente clienteActualizado = clienteService.actualizarCliente(clienteID, datosNuevo);

        assertEquals(3884802489L, cliente.getTelefono());
        assertEquals("juanperez123@gmail.com", cliente.getEmail());
        verify(clienteRepository, times(1)).save((cliente));

    }


}
