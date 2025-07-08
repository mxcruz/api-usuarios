# API Usuarios

API Usuarios es una API Rest que utilizamos para ejercitar buenas prácticas de desarrollo de Software.

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

3. **Ejecutar tests de integración**
   > **Importante:** Los tests de integración utilizan [Karate](https://karatelabs.io/) y una base de datos en memoria H2, por lo que no requieren una base de datos externa.  
   > Para ejecutar las pruebas de integración y generar los reportes, utiliza el siguiente comando:
   ```sh
   ./bin/verify.sh
   ```
   Los reportes de cobertura y resultados de tests de integración pueden visualizarse ejecutando:
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

Si prefieres ejecutar la aplicación desde tu IDE, los servicios estarán disponibles en:

- **API REST:** http://localhost/
- **Swagger UI:** http://localhost/swagger-ui/index.html

---
