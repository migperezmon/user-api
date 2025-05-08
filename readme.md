# User API

Esta es una API para la gestión de usuarios

## Requisitos previos

Antes de ejecutar la aplicación, asegúrate de tener instalados los siguientes programas:

- [Java 17] (https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Gradle 8.1.3] (https://gradle.org/releases/) (opcional, si no usas Docker)
- [Docker] (https://www.docker.com/)
- [Postman] (https://www.postman.com/) (opcional, para probar la API)

---

## Configuración inicial

### Variables de entorno

La aplicación utiliza las siguientes propiedades configuradas en `application.properties`:

- **JWT**:
  - `jwt.secret`: Clave secreta para firmar los tokens JWT. (Ésta está hardcoded pero deberá ser almacenada en algún sistema como AWS SecretsManager e inyectada en los procesos de CI/CD)
  - `jwt.issuer`: Nombre del emisor del token.
  - `jwt.expiration.time`: Tiempo de expiración del token en milisegundos.
  - `jwt.roles`: Roles predeterminados. (No están siendo usados en esta prueba)

- **Regex de validación**:
  - `cl.password.regex`: Expresión regular para validar contraseñas.
  - `cl.email.regex`: Expresión regular para validar correos electrónicos.

Asegúrate de ajustar estas configuraciones según tus necesidades.

---

## Cómo ejecutar la aplicación

### 1. **Ejecutar con Docker**

#### 1.a. Construir la imagen Docker:
```bash
docker build -t user-api .

### 1.b. **Ejecutar el servicio desde la aplicación**
```bash
docker run -p 8080:8080 user-api

### 2. **Ejecutar localmente sin Docker**

#### 2.a. Construir la imagen Docker:
```bash
gradle clean build

### 2.b. **Ejecutar el servicio desde la aplicación**
```bash
java -jar [user-api-0.0.1-SNAPSHOT.jar]