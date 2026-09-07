package com.example.tp2;

import com.example.tp2.model.CajaDeAhorro;
import com.example.tp2.model.Cliente;
import com.example.tp2.model.Transaccion;
import com.example.tp2.repository.CajaDeAhorroRepository;
import com.example.tp2.repository.ClienteRepository;
import com.example.tp2.repository.CuentaFinancieraRepository;
import com.example.tp2.repository.TransaccionRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootApplication
public class Tp2Application {

    public static void main(String[] args) {
        SpringApplication.run(Tp2Application.class, args);
    }
    @Bean
    public CommandLineRunner ejecutar(TransferenciaService transferenciaService) {
        return args -> {
            transferenciaService.realizarPruebaTransferencia();
        };
    }
/*
    @Bean
    public CommandLineRunner ejecutar(ClienteRepository clienteRepository, CuentaFinancieraRepository cuentaRepository, CajaDeAhorroRepository cajaAhorroRepository) {
        return args -> {
            System.out.println("\n========== INICIO DE PRUEBAS JPA ==========");

            // Crear y guardar un nuevo cliente
            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setCuil(20456789019L);
            nuevoCliente.setNombre("José");
            nuevoCliente.setApellido("Pérez");
            nuevoCliente.setFechaCreacion(LocalDateTime.now());
            clienteRepository.save(nuevoCliente);

            System.out.println("Cliente guardado con éxito.");

            // Crear una caja de ahorro relacionada con el cliente
            CajaDeAhorro cajaAhorro = new CajaDeAhorro();
            cajaAhorro.setAlias("JosePerez67");
            //cajaAhorro.setCbu(204567890123456789L);
            cajaAhorro.setSaldo(150000.0);
            cajaAhorro.setCliente(nuevoCliente);
            cajaAhorro.setFechaCreacion(LocalDateTime.now());
            cajaAhorroRepository.save(cajaAhorro);

            System.out.println("Cuenta Caja de Ahorro creada.");

            // Consultar por CUIL e imprimir todos sus datos

            System.out.println("\n--- BÚSQUEDA POR CUIL ---");
            clienteRepository.findByCuil(20456789019L).ifPresentOrElse(
                    cliente -> System.out.println("-> Encontrado: " + cliente),
                    () -> System.out.println("-> No se encontró el cliente.")
            );

            // 3. Imprimir TODOS los datos registrados en la DB
            System.out.println("\n--- TODOS LOS CLIENTES REGISTRADOS ---");
            clienteRepository.findAll().forEach(cliente -> System.out.println(cliente));

            // Si tenés otros repositorios (ej: tarjetaRepository, cuentaRepository), los sumás acá:

            System.out.println("\n--- TODAS LAS CUENTAS REGISTRADAS ---");
            cuentaRepository.findAll().forEach(cuenta -> System.out.println(cuenta));

            System.out.println("===========================================\n");
        };
    }
*/


    @Service
    public class TransferenciaService {

        private final ClienteRepository clienteRepository;
        private final CajaDeAhorroRepository cajaAhorroRepository;
        private final TransaccionRepository transaccionRepository;

        public TransferenciaService(ClienteRepository clienteRepository,
                                    CajaDeAhorroRepository cajaAhorroRepository,
                                    TransaccionRepository transaccionRepository) {
            this.clienteRepository = clienteRepository;
            this.cajaAhorroRepository = cajaAhorroRepository;
            this.transaccionRepository = transaccionRepository;
        }

        @Transactional
        public void realizarPruebaTransferencia() {
            System.out.println("\n========== PRUEBA DE OPERACIÓN: TRANSFERENCIA ==========");

            // 1. Preparar Clientes
            Cliente clienteOrigen = new Cliente();
            clienteOrigen.setCuil(20456789019L);
            clienteOrigen.setNombre("José");
            clienteOrigen.setApellido("Pérez");
            clienteOrigen.setFechaCreacion(LocalDateTime.now());
            clienteOrigen = clienteRepository.save(clienteOrigen);

            Cliente clienteDestino = new Cliente();
            clienteDestino.setCuil(27389998880L);
            clienteDestino.setNombre("María");
            clienteDestino.setApellido("Gómez");
            clienteDestino.setFechaCreacion(LocalDateTime.now());
            clienteDestino = clienteRepository.save(clienteDestino);

            // 2. Preparar Cuentas
            CajaDeAhorro cuentaOrigen = new CajaDeAhorro();
            cuentaOrigen.setCbu(100000000000000001L);
            cuentaOrigen.setSaldo(150000.0);
            cuentaOrigen.setFechaCreacion(LocalDateTime.now());
            cuentaOrigen.setCliente(clienteOrigen);
            cuentaOrigen = cajaAhorroRepository.save(cuentaOrigen);

            CajaDeAhorro cuentaDestino = new CajaDeAhorro();
            cuentaDestino.setCbu(200000000000000002L);
            cuentaDestino.setSaldo(10000.0);
            cuentaDestino.setFechaCreacion(LocalDateTime.now());
            cuentaDestino.setCliente(clienteDestino);
            cuentaDestino = cajaAhorroRepository.save(cuentaDestino);

            // Ejecutar Transferencia
            double montoTransferencia = 25000.0;

            if (cuentaOrigen.getSaldo() >= montoTransferencia) {
                // Al estar dentro de @Transactional, JPA gestiona las modificaciones automáticamente (dirty checking)
                cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - montoTransferencia);
                cuentaDestino.setSaldo(cuentaDestino.getSaldo() + montoTransferencia);

                // Guardar cambios actualizados
                cuentaOrigen = cajaAhorroRepository.save(cuentaOrigen);
                cuentaDestino = cajaAhorroRepository.save(cuentaDestino);

                // Registrar Transacción
                Transaccion transaccion = new Transaccion();
                transaccion.setFecha(new java.util.Date());
                transaccion.setHora(LocalTime.now());
                transaccion.setFechaCreacion(LocalDateTime.now());
                transaccion.setMonto((float) montoTransferencia);
                transaccion.setTipo("TRANSFERENCIA");
                transaccion.setEstadoTransaccion("Exitosa");
                transaccion.setCuenta(cuentaOrigen);

                transaccionRepository.save(transaccion);

                System.out.println("--> Transferencia realizada con éxito!");
            } else {
                System.out.println("--> Fondos insuficientes.");
            }

            // 4. Verificar Resultados
            System.out.println("\n--- RESULTADOS EN BASE DE DATOS ---");
            System.out.println("Saldo Origen (CBU " + cuentaOrigen.getCbu() + "): $" + cuentaOrigen.getSaldo());
            System.out.println("Saldo Destino (CBU " + cuentaDestino.getCbu() + "): $" + cuentaDestino.getSaldo());

            System.out.println("\nTransacciones registradas:");
            transaccionRepository.findAll().forEach(t ->
                    System.out.println("-> ID / Hora: " + t.getHora() +
                            " | Monto: $" + t.getMonto() +
                            " | Tipo: " + t.getTipo() +
                            " | Estado: " + t.getEstadoTransaccion())
            );

            System.out.println("========================================================\n");
        }
    }
}