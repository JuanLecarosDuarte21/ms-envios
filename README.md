# Microservicio de Envíos — Tienda PC

## Descripción

Microservicio desarrollado con Java y Spring Boot para administrar los envíos de una tienda en línea de componentes de PC.

Permite registrar envíos, consultar su información, buscar por pedido, estado o transportista, actualizar su estado y número de seguimiento, y eliminar registros.

## Tecnologías utilizadas

- Java 21
- Spring Boot 4.0.8
- Spring Data JPA
- MySQL
- Maven
- Git y GitHub

## Configuración

- **Puerto:** 8087
- **Base de datos:** `tienda_envios_db`
- **URL base:** `http://localhost:8087/api/envios`

## Funcionalidades

- Registrar envíos con estado inicial `PENDIENTE`.
- Consultar todos los envíos.
- Buscar envíos por ID.
- Consultar envíos asociados a un pedido.
- Filtrar envíos por estado.
- Buscar envíos por empresa transportista.
- Filtrar por pedido y estado.
- Actualizar el estado de un envío.
- Actualizar el número de seguimiento.
- Eliminar envíos.

## Endpoints REST

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/envios` | Crear envío |
| GET | `/api/envios` | Listar envíos |
| GET | `/api/envios/{id}` | Buscar por ID |
| GET | `/api/envios/pedido/{pedidoId}` | Buscar por pedido |
| GET | `/api/envios/estado/{estado}` | Buscar por estado |
| GET | `/api/envios/transportista/{transportista}` | Buscar por transportista |
| GET | `/api/envios/pedido/{pedidoId}/estado/{estado}` | Filtrar por pedido y estado |
| PUT | `/api/envios/{id}/estado` | Actualizar estado |
| PUT | `/api/envios/{id}/seguimiento` | Actualizar número de seguimiento |
| DELETE | `/api/envios/{id}` | Eliminar envío |

## Ejemplo para crear un envío

**POST** `/api/envios`

```json
{
  "pedidoId": 1,
  "direccionEntrega": "Av. Principal 123, Santiago",
  "transportista": "STARKEN",
  "numeroSeguimiento": "ENV123456"
}
```

El sistema asigna automáticamente el estado inicial `PENDIENTE` y la fecha de creación del registro.

## Ejemplo para actualizar el estado

**PUT** `/api/envios/1/estado`

```json
{
  "estado": "EN_TRANSITO"
}
```

Otros estados utilizados en la simulación son `PENDIENTE` y `ENTREGADO`.

## Ejemplo para actualizar el seguimiento

**PUT** `/api/envios/1/seguimiento`

```json
{
  "numeroSeguimiento": "ENV987654"
}
```

## Base de datos

Crear la base de datos en MySQL:

```sql
CREATE DATABASE IF NOT EXISTS tienda_envios_db;
```

Spring Data JPA e Hibernate administran la tabla `envios`.

## Ejecución

Configurar la variable de entorno `DB_PASSWORD` con la contraseña local de MySQL.

En PowerShell:

```powershell
$env:DB_PASSWORD = "TU_CONTRASEÑA_MYSQL"
.\mvnw.cmd spring-boot:run
```

## Compilación

```powershell
.\mvnw.cmd clean package
```

## Estructura del proyecto

- `model`: entidad Envio.
- `repository`: acceso a la base de datos.
- `service`: lógica de negocio.
- `controller`: endpoints REST.
- `config`: configuraciones adicionales.

## Alcance actual

El microservicio administra registros de envíos simulados. No está integrado con empresas de transporte reales ni verifica automáticamente la existencia del pedido en `ms-pedidos`.

## Proyecto académico

Este microservicio forma parte de una arquitectura de ocho microservicios para una tienda de componentes de PC.