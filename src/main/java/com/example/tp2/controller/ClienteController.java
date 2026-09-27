package com.example.tp2.controller;

import com.example.tp2.dto.ClienteRequestDto;
import com.example.tp2.dto.ClienteResponseDto;
import com.example.tp2.model.Cliente;
import com.example.tp2.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clientes")

public class ClienteController {
    //el servicio de cliente
    private final ClienteService clienteService;
    //Crear cliente
    @PostMapping
    public ResponseEntity<ClienteResponseDto> registrarCliente(@Valid @RequestBody
    ClienteRequestDto dto){

        //se instancia un cliente vacio de DTO a entidad
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(dto.getNombre());
        nuevoCliente.setApellido(dto.getApellido());
        nuevoCliente.setCuil(dto.getCuil());
        nuevoCliente.setEmail(dto.getEmail());
        nuevoCliente.setTelefono(dto.getTelefono());
        nuevoCliente.setDireccion(dto.getDireccion());

        //guardar atravez del servicio
        Cliente clienteGuardado = clienteService.crearCliente(nuevoCliente);

        //se intancia un cliente vacio de Entidad a Dto para respuesta
        ClienteResponseDto responseDto = new ClienteResponseDto();
        responseDto.setId(clienteGuardado.getId());
        responseDto.setNombre(clienteGuardado.getNombre());
        responseDto.setApellido(clienteGuardado.getApellido());
        responseDto.setCuil(clienteGuardado.getCuil());
        responseDto.setEmail(clienteGuardado.getEmail());

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
//mostrar todas las listas
    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> listarCliente(){
        //la lista de clientes
        List<Cliente> clientes = clienteService.listartodos();
        //
        List<ClienteResponseDto> responseDtos = clientes.stream().map(c -> {
            ClienteResponseDto clienteDto = new ClienteResponseDto();
            clienteDto.setNombre(c.getNombre());
            clienteDto.setApellido(c.getApellido());
            clienteDto.setEmail(c.getEmail());
            clienteDto.setCuil(c.getCuil());
            clienteDto.setId(c.getId());
            return clienteDto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(responseDtos);
    }
}
