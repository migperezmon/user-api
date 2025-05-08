# User API

Esta es una API para la gestión de usuarios

## Requisitos previos

Antes de ejecutar la aplicación, asegúrate de tener instalados los siguientes programas:

- [Java 17] 
- [Gradle 8.1.3] 
- [Docker] 
- [Postman]

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

## Script Base de datos

El Script para la creación de la base de datos se encuentra dentro de la ruta raíz del proyecto
```bash
./schema.sql
```
La aplicación está configurada para que construya automáticamente el modelo de datos en la base de datos en memoria H2

## Diagrama de Secuencia

El diagrama de solución se encuentra en la raíz del pryecto

./Diagrama de Secuencia.png

La aplicación está configurada para que construya automáticamente el modelo de datos en la base de datos en memoria H2

---------------------

## Cómo ejecutar la aplicación

### 1. **Ejecutar con Docker**

#### 1.a. Construir la imagen Docker:
```bash
docker build -t user-api .
```
### 1.b. **Ejecutar el servicio desde la aplicación**
```bash
docker run -p 8080:8080 user-api
```
### 2. **Ejecutar localmente sin Docker**

#### 2.a. Construir la imagen Docker:
```bash
gradle clean build
```
### 2.b. **Ejecutar el servicio desde la aplicación**
```bash
java -jar [user-api-0.0.1-SNAPSHOT.jar]
```