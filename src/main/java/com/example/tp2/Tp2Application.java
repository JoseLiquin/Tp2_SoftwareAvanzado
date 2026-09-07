package com.example.tp2;

import com.example.tp2.model.CajaDeAhorro;
import com.example.tp2.model.Cliente;
import com.example.tp2.model.Transaccion;
import com.example.tp2.repository.CajaDeAhorroRepository;
import com.example.tp2.repository.ClienteRepository;
import com.example.tp2.repository.TransaccionRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@SpringBootApplication
public class Tp2Application {

    public static void main(String[] args) {
        SpringApplication.run(Tp2Application.class, args);
    }
/*
    @Bean
    public CommandLineRunner ejecutar(OperacionesBancariasService operacionesService) {
        return args -> {
            System.out.println("\n______________________________________________________");
            System.out.println("          INICIO DE PRUEBAS DE OPERACIONES              ");
            System.out.println("________________________________________________________\n");

            //carga inicial de cliente y cuenta de prueba
            CajaDeAhorro cuenta = operacionesService.inicializarDatosPrueba();

            // prueba de operaciones -etapa 1-
            operacionesService.realizarDeposito(cuenta.getCbu(), 50000.0);
            operacionesService.realizarExtraccion(cuenta.getCbu(), 15000.0);
            operacionesService.realizarCalculoInteres(cuenta.getCbu());
            operacionesService.realizarTransferencia(cuenta.getCbu(), 20000.0);

            // resultado
            operacionesService.mostrarHistorialYEstados(cuenta.getCbu());
        };
    }

    @Service
    public static class OperacionesBancariasService {

        private final ClienteRepository clienteRepository;
        private final CajaDeAhorroRepository cajaAhorroRepository;
        private final TransaccionRepository transaccionRepository;

        public OperacionesBancariasService(ClienteRepository clienteRepository,
                                           CajaDeAhorroRepository cajaAhorroRepository,
                                           TransaccionRepository transaccionRepository) {
            this.clienteRepository = clienteRepository;
            this.cajaAhorroRepository = cajaAhorroRepository;
            this.transaccionRepository = transaccionRepository;
        }

        @Transactional
        public CajaDeAhorro inicializarDatosPrueba() {
            Cliente cliente = new Cliente();
            cliente.setCuil(20456789019L);
            cliente.setNombre("José");
            cliente.setApellido("Pérez");
            cliente.setFechaCreacion(LocalDateTime.now());
            cliente = clienteRepository.save(cliente);

            CajaDeAhorro cuenta = new CajaDeAhorro(100000000000000001L, "JosePerez.Ahorro", 100000.0, "ACTIVA", 5, 12.0);
            cuenta.setFechaCreacion(LocalDateTime.now());
            cuenta.setCliente(cliente);
            return cajaAhorroRepository.save(cuenta);

        }

        // == OPERACIÓN 1: DEPÓSITO ==
        @Transactional
        public void realizarDeposito(Long cbu, double monto) {
            System.out.println("---> Ejecutando Operación: DEPÓSITO");
            cajaAhorroRepository.findById(cbu).ifPresent(cuenta -> {
                cuenta.depositar(monto);
                cajaAhorroRepository.save(cuenta);
                registrarTransaccion(cuenta, monto, "DEPÓSITO", "Exitosa");
            });
        }

        // == OPERACIÓN 2: EXTRACCIÓN ==
        @Transactional
        public void realizarExtraccion(Long cbu, double monto) {
            System.out.println("\n---> Ejecutando Operación: EXTRACCIÓN");
            cajaAhorroRepository.findById(cbu).ifPresent(cuenta -> {
                boolean exito = cuenta.extraer(monto);
                cajaAhorroRepository.save(cuenta);

                String estado = exito ? "Exitosa" : "Rechazada";
                registrarTransaccion(cuenta, monto, "EXTRACCIÓN", estado);
            });
        }

        // == OPERACIÓN 3: CÁLCULO DE INTERÉS ==
        @Transactional
        public void realizarCalculoInteres(Long cbu) {
            System.out.println("\n---> Ejecutando Operación: CÁLCULO DE INTERÉS");
            cajaAhorroRepository.findById(cbu).ifPresent(cuenta -> {
                double saldoAnterior = cuenta.getSaldo();
                cuenta.calcularInteres(); // Llama al método de CajaDeAhorro
                cajaAhorroRepository.save(cuenta);

                double montoGenerado = cuenta.getSaldo() - saldoAnterior;
                registrarTransaccion(cuenta, montoGenerado, "INTERÉS", "Exitosa");
            });
        }

        // == OPERACIÓN 4: TRANSFERENCIA ==
        @Transactional
        public void realizarTransferencia(Long cbuOrigen, double monto) {
            System.out.println("\n---> Ejecutando Operación: TRANSFERENCIA");

            //crear cliente y cuenta destino de prueba
            Cliente clienteDestino = new Cliente();
            clienteDestino.setCuil(27389998880L);
            clienteDestino.setNombre("María");
            clienteDestino.setApellido("Gómez");
            clienteDestino.setFechaCreacion(LocalDateTime.now());
            clienteDestino = clienteRepository.save(clienteDestino);

            CajaDeAhorro cuentaDestino = new CajaDeAhorro(200000000000000002L, "MariaGomez.Ahorro", 10000.0, "ACTIVA", 5, 12.0);
            cuentaDestino.setFechaCreacion(LocalDateTime.now());
            cuentaDestino.setCliente(clienteDestino);
            cuentaDestino = cajaAhorroRepository.save(cuentaDestino);

            CajaDeAhorro origen = cajaAhorroRepository.findById(cbuOrigen).orElse(null);

            if (origen != null) {
                if (origen.getSaldo() >= monto) {
                    origen.enviarTransferencia(monto, cuentaDestino.getCbu()); // Llama al método de CuentaFinanciera
                    cuentaDestino.depositar(monto);

                    cajaAhorroRepository.save(origen);
                    cajaAhorroRepository.save(cuentaDestino);

                    registrarTransaccion(origen, monto, "TRANSFERENCIA_ENVIADA", "Exitosa");
                    registrarTransaccion(cuentaDestino, monto, "TRANSFERENCIA_RECIBIDA", "Exitosa");
                } else {
                    origen.enviarTransferencia(monto, cuentaDestino.getCbu()); // Imprime "Saldo insuficiente"
                    registrarTransaccion(origen, monto, "TRANSFERENCIA_ENVIADA", "Rechazada");
                }
            }
        }

        // Método auxiliar para registrar la auditoría en BD
        private void registrarTransaccion(CajaDeAhorro cuenta, double monto, String tipo, String estado) {
            Transaccion transaccion = new Transaccion();
            transaccion.setFecha(new Date());
            transaccion.setHora(LocalTime.now());
            transaccion.setFechaCreacion(LocalDateTime.now());
            transaccion.setMonto(monto);
            transaccion.setTipo(tipo);
            transaccion.setEstadoTransaccion(estado);
            transaccion.setCuenta(cuenta);
            transaccionRepository.save(transaccion);
        }

        @Transactional
        public void mostrarHistorialYEstados(Long cbu) {
            System.out.println("\n______________________________________________________");
            System.out.println("           HISTORIAL DE TRANSACCIONES Y AUDITORÍA       ");
            System.out.println("-________________________________________________________");

            cajaAhorroRepository.findById(cbu).ifPresent(cuenta ->
                    System.out.println("Estado final Cuenta CBU " + cuenta.getCbu() + ": $" + cuenta.getSaldo())
            );

            System.out.println("\nRegistros Auditados:");
            transaccionRepository.findAll().forEach(t ->
                    System.out.println("-> Hora: " + t.getHora() +
                            " | Monto: $" + t.getMonto() +
                            " | Tipo: " + t.getTipo() +
                            " | Estado: " + t.getEstadoTransaccion())
            );
        }
    }*/
}