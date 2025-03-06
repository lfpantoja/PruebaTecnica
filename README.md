PRUEBA TECNICA

SQL
CREATE DATABASE pruebatecnica;

CREATE TABLE persona (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(20),
    edad INT NOT NULL,
    identificacion VARCHAR(50) UNIQUE NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(20)
);

CREATE TABLE cliente (
    cliente_id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(20),
    edad INT NOT NULL,
    identificacion VARCHAR(50) UNIQUE NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(20),
    contrasena VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE cuenta (
    id SERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(50) UNIQUE NOT NULL,
    tipo_cuenta VARCHAR(20) NOT NULL CHECK (tipo_cuenta IN ('Ahorros', 'Corriente')),
    saldo_inicial DECIMAL(15,2) NOT NULL DEFAULT 0,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    cliente_id INT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(cliente_id) ON DELETE CASCADE
);

CREATE TABLE movimiento (
    id SERIAL PRIMARY KEY,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo_movimiento VARCHAR(20) NOT NULL CHECK (tipo_movimiento IN ('Ingreso', 'Retiro')),
    valor DECIMAL(15,2) NOT NULL,
    saldo DECIMAL(15,2) NOT NULL,
    cuenta_id INT NOT NULL,
    FOREIGN KEY (cuenta_id) REFERENCES cuenta(id) ON DELETE CASCADE
);


INSERT INTO persona (nombre, genero, edad, identificacion, direccion, telefono)
VALUES 
    ('Jose Lema', 'Masculino', 30, 'ID1001', 'Otavalo sn y principal', '098254785'),
    ('Marianela Montalvo', 'Femenino', 28, 'ID1002', 'Amazonas y NN.UU.', '097548965'),
    ('Juan Osorio', 'Masculino', 35, 'ID1003', '13 junio y Equinoccial', '098874587');

select * from persona;
select * from cliente;
select * from movimiento;


PRUEBAS DE FUNCIONAMIENTO
----CLIENTES----
POST
http://localhost:8080/api/clientes
{
    "nombre": "Juan Chochos",
    "genero": "Masculino",
    "edad": 30,
    "identificacion": "ID1004",
    "direccion": "Otavalo sn y principal",
    "telefono": "098254786",
    "contrasena": "1234",
    "estado": true
}

DELETE
http://localhost:8080/api/clientes/8
{
    "nombre": "Juan Chochos",
    "genero": "Masculino",
    "edad": 30,
    "identificacion": "ID1004",
    "direccion": "Otavalo sn y principal",
    "telefono": "098254786",
    "contrasena": "1234",
    "estado": true
}

PUT
http://localhost:8080/api/clientes/5
{
    "nombre": "Jose Lemas",
    "genero": "Masculino",
    "edad": 31,
    "identificacion": "ID1001",
    "direccion": "Otavalo sn y principal",
    "telefono": "098254785",
    "contrasena": "4321",
    "estado": true
}

GET
http://localhost:8080/api/clientes

----CUENTAS----
GET
http://localhost:8080/api/cuentas

POST
http://localhost:8080/api/cuentas
{
    "numeroCuenta": "585545",
    "tipoCuenta": "Corriente",
    "saldoInicial": 1000,
    "estado": true,
    "cliente": {
        "clienteId": 5
    }
}

PUT
http://localhost:8080/api/cuentas/12
{
    "numeroCuenta": "000001",
    "tipoCuenta": "Ahorros",
    "saldoInicial": 1,
    "estado": true,
    "cliente": {
        "clienteId": 8
    }
}

DELETE
http://localhost:8080/api/cuentas/12


----MOVIMIENTOS----
POST
http://localhost:8080/api/movimientos
{
    "tipoMovimiento": "Retiro",
    "valor": -15,
    "cuenta": {
        "id": 11
    }
}

GET
http://localhost:8080/api/movimientos/buscar?fecha=2025-03-05&clienteId=5
