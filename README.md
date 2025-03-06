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
    "nombre": "Jose Lema",
    "genero": "Masculino",
    "edad": 30,
    "identificacion": "ID1001",
    "direccion": "Otavalo sn y principal",
    "telefono": "098254785",
    "contraseña": "1234",
    "estado": true
}
{
    "nombre": "Marianela Montalvo",
    "genero": "Femenino",
    "edad": 29,
    "identificacion": "ID1002",
    "direccion": "Amazonas y NNUU",
    "telefono": "097548965",
    "contraseña": "5678",
    "estado": true
}
{
    "nombre": "Juan Osorio",
    "genero": "Masculino",
    "edad": 29,
    "identificacion": "ID1003",
    "direccion": "13 junio y Equinoccial",
    "telefono": "098874587",
    "contraseña": "1245",
    "estado": true
}
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
http://localhost:8080/api/clientes/4

PUT
http://localhost:8080/api/clientes/1
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
        "numeroCuenta": "478758",
        "tipoCuenta": "Ahorros",
        "saldoInicial": 2000,
        "estado": true,
        "cliente": {
            "clienteId": 1
        }
    }
    {
        "numeroCuenta": "225487",
        "tipoCuenta": "Corriente",
        "saldoInicial": 100,
        "estado": true,
        "cliente": {
            "clienteId": 2
        }
    }
    {
        "numeroCuenta": "495878",
        "tipoCuenta": "Ahorros",
        "saldoInicial": 0,
        "estado": true,
        "cliente": {
            "clienteId": 3
        }
    }
    {
        "numeroCuenta": "496825",
        "tipoCuenta": "Ahorros",
        "saldoInicial": 540,
        "estado": true,
        "cliente": {
            "clienteId": 2
        }
    }
    {
        "numeroCuenta": "585545",
        "tipoCuenta": "Corriente",
        "saldoInicial": 1000,
        "estado": true,
        "cliente": {
            "clienteId": 1
        }
    }

PUT
http://localhost:8080/api/cuentas/5
{
    "numeroCuenta": "585545",
    "tipoCuenta": "Corriente",
    "saldoInicial": 1001,
    "estado": true,
    "cliente": {
        "clienteId": 1
    }
}

DELETE
http://localhost:8080/api/cuentas/5


----MOVIMIENTOS----
POST
http://localhost:8080/api/movimientos
{
    "tipoMovimiento": "Retiro",
    "valor": -15,
    "cuenta": {
        "id": 1
    }
}

GET
http://localhost:8080/api/movimientos/buscar?fecha=2025-03-05&clienteId=5
