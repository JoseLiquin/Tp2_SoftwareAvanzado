-- Desactivar temporalmente restricciones de clave foránea
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE transacciones;
TRUNCATE TABLE cajas_ahorros;
TRUNCATE TABLE cuentas_corrientes;
TRUNCATE TABLE cuentas_financieras;
TRUNCATE TABLE clientes;

SET FOREIGN_KEY_CHECKS = 1;

-- =============================================================================
-- 1. INSERCIÓN DE CLIENTES
-- =============================================================================
INSERT INTO clientes (id, nombre, apellido, cuil, email, telefono, direccion, fecha_creacion, fecha_modificacion) VALUES
                                                                                                                      ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Juan', 'Pérez', '20301112229', 'juan.perez@email.com', 3884123456, 'Av. Italia 123', NOW(), NOW()),
                                                                                                                      ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'María', 'Gómez', '27312223334', 'maria.gomez@email.com', 3884987654, 'Belgrano 456', NOW(), NOW()),
                                                                                                                      ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Carlos', 'López', '20323334445', 'carlos.lopez@email.com', 3884112233, 'Lavalle 789', NOW(), NOW());

-- =============================================================================
-- 2. INSERCIÓN DE CUENTAS (Tabla Padre: cuentas_financieras)
-- =============================================================================
INSERT INTO cuentas_financieras (id, cbu, alias, saldo, estado, cliente_cuil, fecha_creacion, fecha_modificacion) VALUES
                                                                                                                      ('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b11', '31000000000001', 'JUAN.PEREZ.ARS', 150000.50, 'ACTIVA', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', NOW(), NOW()),
                                                                                                                      ('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b12', '31000000000002', 'MARIA.GOMEZ.ARS', 320000.75, 'ACTIVA', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', NOW(), NOW());

-- INSERCIÓN EN SUBTABLAS DE HERENCIA (JOINED)
INSERT INTO cajas_ahorros (id, cupo_limite, interes_anual, extracciones_realizadas) VALUES
    ('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b11', 5, 45.0, 1);

INSERT INTO cuentas_corrientes (id, limite_descubierto, comision_mantenimiento) VALUES
    ('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b12', 50000.0, 1500.0);

-- =============================================================================
-- 3. INSERCIÓN DE TRANSACCIONES
-- =============================================================================
INSERT INTO transacciones (nro_comprobante, fecha, hora, monto, tipo, estado_transaccion, cuenta_cbu, fecha_creacion, fecha_modificacion) VALUES
                                                                                                                                              ('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380c11', CURDATE(), CURTIME(), 50000.00, 'DEPOSITO', 'COMPLETADA', 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b11', NOW(), NOW()),
                                                                                                                                              ('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380c12', CURDATE(), CURTIME(), 12000.50, 'EXTRACCION', 'COMPLETADA', 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b11', NOW(), NOW());