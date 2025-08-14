# API Usuarios

API REST completa con autenticación JWT desarrollada con Spring Boot 3 y Java 21, implementando buenas prácticas de desarrollo de software y arquitectura hexagonal.

## 🚀 Características Principales

- **Autenticación JWT completa**: Login, registro y refresh de tokens
- **Gestión de usuarios**: CRUD completo con validaciones
- **Arquitectura hexagonal**: Separación clara de responsabilidades
- **Seguridad**: Spring Security con JWT Bearer tokens
- **Documentación automática**: Swagger/OpenAPI 3
- **Base de datos**: PostgreSQL para producción, H2 para testing
- **Monitoreo**: Spring Boot Actuator
- **Containerización**: Docker y Docker Compose listos para usar

## 🛠️ Stack Tecnológico

### Backend
- **Java 21** - Lenguaje de programación
- **Spring Boot 3.3.0** - Framework principal
- **Spring Security** - Seguridad y autenticación
- **Spring Data JPA** - Persistencia de datos
- **JWT (jjwt)** - Gestión de tokens JSON Web Token
- **PostgreSQL** - Base de datos principal
- **H2** - Base de datos en memoria para testing

### Herramientas de Desarrollo
- **Maven** - Gestión de dependencias y build
- **Lombok** - Reducción de boilerplate code
- **MapStruct** - Mapeo entre DTOs y entidades
- **SpringDoc OpenAPI** - Documentación automática de APIs

### Testing
- **JUnit 5** - Framework de testing unitario
- **Karate** - Testing de integración y API testing
- **Spring Boot Test** - Testing de componentes Spring
- **Mockito** - Mocking para unit tests

### DevOps & Herramientas
- **Docker** - Containerización
- **Docker Compose** - Orquestación de servicios
- **JaCoCo** - Cobertura de código
- **Maven Surefire/Failsafe** - Ejecución de tests

## 📁 Arquitectura del Proyecto

El proyecto implementa **Arquitectura Hexagonal (Ports & Adapters)** con la siguiente estructura:

```
src/main/java/com/maxicruz/usuarios/
├── domain/           # Lógica de negocio pura
│   ├── models/       # Entidades del dominio
│   ├── ports/        # Interfaces (puertos)
│   └── exception/    # Excepciones del dominio
├── application/      # Casos de uso y servicios
│   ├── services/     # Servicios de aplicación
│   └── usecases/     # Casos de uso específicos
└── infrastructure/   # Adaptadores externos
    ├── adapters/     # Implementaciones de puertos
    ├── config/       # Configuraciones Spring
    └── security/     # Configuración de seguridad
```

## 🔐 Endpoints de la API

### Autenticación
- `POST /auth/login` - Iniciar sesión
- `POST /auth/register` - Registrar nuevo usuario
- `POST /auth/refresh` - Renovar access token

### Gestión de Usuarios
- `GET /api/v1/users` - Listar todos los usuarios
- `GET /api/v1/users/{id}` - Obtener usuario por ID
- `GET /api/v1/users/me` - Obtener datos del usuario autenticado

## Primeros Pasos

A continuación se detallan los pasos para obtener una copia funcional de la API en tu entorno local, tanto para desarrollo como para pruebas.

### Requisitos Previos

Antes de comenzar, asegúrate de contar con lo siguiente:

* [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
    * (Se recomienda gestionar versiones de JDK con [Jenv](https://www.jenv.be/))
* [IntelliJ IDEA](https://www.jetbrains.com/es-es/idea/download/) u otro IDE compatible
* [Docker Desktop](https://www.docker.com/products/docker-desktop/)
* [Docker Compose](https://docs.docker.com/compose/install/)

### Instalación y Ejecución

Sigue estos pasos para preparar el entorno de desarrollo:

1. **Clonar el repositorio**
   ```sh
   git clone git@github.com:mxcruz/api-usuarios.git
   cd api-usuarios
   ```

2. **Ejecutar tests unitarios**
   ```sh
   ./mvnw clean test
   ```

3. **Ejecutar tests de integración con Karate**
   > **Importante:** Los tests de integración utilizan [Karate Framework](https://karatelabs.io/) para pruebas de API y una base de datos en memoria H2, por lo que no requieren una base de datos externa.  
   > Para ejecutar las pruebas de integración y generar los reportes, utiliza el siguiente comando:
   ```sh
   ./mvnw clean verify
   ```
   Al finalizar la ejecución, se generará automáticamente un archivo **`target/index.html`** que centraliza todos los reportes:
   - **Cobertura de código** (JaCoCo)
   - **Resultados de tests unitarios** (Surefire)
   - **Resultados de tests de integración** (Karate)
   
   Para consultar todos los reportes desde una sola página, ejecuta:
   ```sh
   open target/index.html
   ```

4. **Compilar el proyecto**
   ```sh
   ./mvnw clean package
   ```

5. **Levantar el stack completo (API + Base de Datos) con Docker**
    - Construir la imagen Docker:
      ```sh
      ./bin/build.sh
      ```
    - Levantar el stack:
      ```sh
      ./bin/up.sh
      ```

### Inicialización con Datos de Prueba

Para iniciar el stack con datos de prueba, modifica la siguiente configuración en `./src/resources/application.yml`:

```yml
data:
  initialization:
    enabled: true
```
> **Importante:** Después de este cambio, recompila y vuelve a levantar el stack para aplicar la configuración.

## Opciones de Uso

Una vez que los servicios estén en funcionamiento, puedes acceder a:

- **API REST:** http://localhost:8080/
- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- **Base de datos PostgreSQL:** localhost:5432

Si prefieres ejecutar la aplicación desde tu IDE, los servicios estarán disponibles en:

- **API REST:** http://localhost/
- **Swagger UI:** http://localhost/swagger-ui/index.html

## 🧪 Estrategia de Testing

El proyecto implementa una estrategia de testing completa:

### Tests Unitarios (JUnit 5 + Mockito)
- Cobertura de servicios y casos de uso
- Mocking de dependencias externas
- Validación de lógica de negocio

### Tests de Integración (Karate)
- **Features de autenticación:**
  - `auth/login.feature` - Testing de login
  - `auth/register.feature` - Testing de registro
  - `auth/refresh.feature` - Testing de refresh token
- **Features de usuarios:**
  - `get-users.feature` - Listado de usuarios
  - `get-user-data.feature` - Datos del usuario autenticado

### Ejecutar tests específicos
```sh
# Solo tests unitarios
./mvnw test

# Solo tests de integración
./mvnw failsafe:integration-test

# Todos los tests con reportes
./mvnw clean verify
```

## 🚀 Comandos de Desarrollo

### Scripts de automatización disponibles:
```sh
# Construcción de imagen Docker
./bin/build.sh

# Levantar stack completo
./bin/up.sh

# Iniciar y esperar que los servicios estén listos
./bin/start-and-wait.sh

# Detener Spring Boot
./bin/stop-spring.sh

# Generar índice de reportes (se ejecuta automáticamente con mvnw verify)
./bin/generate-report-index.sh
```

> 📋 **Nota:** El script `generate-report-index.sh` se ejecuta automáticamente al correr `mvnw clean verify` y genera el archivo `target/index.html` con enlaces a todos los reportes.

## 🐳 Docker y Desarrollo

El proyecto incluye configuración completa para desarrollo con Docker:

- **Dockerfile** optimizado para Spring Boot
- **docker-compose.yml** con PostgreSQL
- Scripts de automatización en `/bin`
- Inicialización de base de datos en `/docker/postgres/init`

## 🔧 Configuración

### Variables de entorno importantes:
- `JWT_SECRET_KEY` - Clave secreta para firmar JWT tokens
- `SPRING_PROFILES_ACTIVE` - Perfil activo (dev, test, prod)
- `DATABASE_URL` - URL de conexión a PostgreSQL

### Perfiles disponibles:
- **dev** - Desarrollo con datos de prueba
- **test** - Testing con H2 en memoria
- **it** - Tests de integración
- **prod** - Producción con PostgreSQL

## 📊 Reportes y Métricas

Después de ejecutar `./mvnw clean verify`, se generará automáticamente un **`target/index.html`** que actúa como página principal para acceder a todos los reportes:

### 🏠 Página principal de reportes:
- **target/index.html** - Dashboard central con enlaces a todos los reportes

### 📁 Reportes individuales:
- **target/site/jacoco/index.html** - Reporte de cobertura de código
- **target/site/surefire-report.html** - Reportes de tests unitarios  
- **target/karate-reports/karate-summary.html** - Reportes detallados de Karate

> 💡 **Tip:** Solo necesitas abrir `target/index.html` para acceder a todos los reportes desde una interfaz centralizada.

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📝 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

---
