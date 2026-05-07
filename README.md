# ✈️ FlyTrack — AeroPuerto Smart

Sistema de gestión de vuelos para el aeropuerto regional AeroPuerto Smart. Permite a los pasajeros consultar itinerarios, recibir notificaciones en tiempo real sobre cambios de vuelo, conocer la puerta de embarque y reportar inconvenientes con su equipaje.

## Stack Tecnológico

| Tecnología | Versión |
|---|---|
| Java | 21 |
| Spring Boot | 3.4.5 |
| Spring Security + JWT | jjwt 0.12.6 |
| WebSocket / STOMP | Spring Boot Starter WebSocket |
| Base de Datos | MySQL 8 / Cloud SQL |
| Documentación API | Springdoc OpenAPI (Swagger UI) |
| Contenerización | Docker (multi-stage) |
| CI/CD | GitHub Actions → Google Cloud Run |

## Arquitectura de Capas

```
com.example.flytrack/
├── config/        SecurityConfig, SwaggerConfig, WebSocketConfig, DataInitializer
├── controllers/   10 controllers REST + WebSocket
├── dtos/          22 DTOs Request/Response
├── entities/      8 entidades JPA
├── enums/         4 enums de dominio
├── mappers/       8 mappers Entity ↔ DTO
├── repositories/  8 repositorios JPA
├── security/      JwtUtil, JwtAuthFilter, CustomUserDetailsService
└── services/      10 servicios de negocio
```

## Roles

| Rol | Descripción |
|---|---|
| `admin` | Administrador del aeropuerto — CRUD completo |
| `pasajero` | Pasajero registrado — ver reservas, equipaje, notificaciones |

## Ejecutar en local (con Docker)

```bash
# Levantar MySQL + aplicación
docker-compose up --build

# La API estará disponible en:
http://localhost:8080/swagger-ui/index.html
```

## Ejecutar en local (sin Docker)

```bash
# 1. Copia el ejemplo de configuración local
cp src/main/resources/application-local.properties.example src/main/resources/application-local.properties

# 2. Ajusta los valores en application-local.properties

# 3. Arranca con el perfil local
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

## Variables de Entorno (Cloud Run)

| Variable | Descripción |
|---|---|
| `DB_NAME` | Nombre de la base de datos Cloud SQL |
| `DB_USER` | Usuario de base de datos |
| `DB_PASSWORD` | Contraseña de base de datos |
| `CLOUD_SQL_CONNECTION_NAME` | Nombre de conexión Cloud SQL (`project:region:instance`) |
| `JWT_SECRET` | Secreto JWT (mínimo 32 caracteres) |
| `CORS_ALLOWED_ORIGINS` | Orígenes permitidos separados por coma |
| `MAIL_USERNAME` | Correo Gmail para notificaciones |
| `MAIL_PASSWORD` | Contraseña de aplicación Gmail |

## Endpoints Principales

| Método | Ruta | Acceso | Descripción |
|---|---|---|---|
| POST | `/auth/login` | Público | Iniciar sesión |
| POST | `/auth/registro` | Público | Registrar pasajero |
| GET | `/vuelos` | Público | Listar todos los vuelos |
| GET | `/vuelos/estado/{estado}` | Público | Filtrar por estado |
| PUT | `/vuelos/{id}` | Admin | Actualizar vuelo (notifica WS) |
| GET | `/reservas/mis-reservas` | Pasajero | Ver mis reservas |
| POST | `/reservas` | Pasajero/Admin | Crear reserva |
| GET | `/equipaje/reserva/{id}` | Pasajero | Ver equipaje |
| POST | `/equipaje/{id}/reportar-incidente` | Pasajero | Reportar incidente |
| GET | `/notificaciones` | Pasajero | Ver notificaciones |
| PATCH | `/notificaciones/{id}/leer` | Pasajero | Marcar como leída |

## WebSocket

Conectarse a `ws://localhost:8080/ws` usando un cliente STOMP (SockJS).

| Destino | Tipo | Descripción |
|---|---|---|
| `/topic/vuelos` | Broadcast | Actualizaciones del tablero de vuelos |
| `/user/queue/notificaciones` | Individual | Notificaciones personalizadas por pasajero |

## Pruebas

```bash
./mvnw test
```

## CI/CD (GitHub Actions)

| Pipeline | Trigger | Acción |
|---|---|---|
| `ci.yml` | Push a `main`, `develop`, `feature/**` | Build + Tests |
| `cd.yml` | Push a `main` o tag `v*` | Deploy a Cloud Run |

### Secrets requeridos en GitHub

- `GCP_PROJECT_ID`, `GCP_SA_KEY` (Service Account Key JSON)
- `DB_NAME`, `DB_USER`, `DB_PASSWORD`, `CLOUD_SQL_CONNECTION_NAME`
- `JWT_SECRET`, `CORS_ALLOWED_ORIGINS`, `MAIL_USERNAME`, `MAIL_PASSWORD`

## Credenciales por defecto (desarrollo)

```
Correo:    admin@flytrack.com
Password:  Admin1234
```
