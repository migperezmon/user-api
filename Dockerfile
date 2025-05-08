# Usa una imagen base de Gradle con JDK 17 y Gradle 8.1.3
FROM gradle:8.13-jdk17 AS build

# Cambia al usuario root para evitar problemas de permisos
USER root

# Copia los archivos del proyecto al contenedor
COPY . /home/gradle/project

# Establece el directorio de trabajo
WORKDIR /home/gradle/project

# Configura un directorio de caché para Gradle y ajusta permisos
ENV GRADLE_USER_HOME=/home/gradle/.gradle
RUN mkdir -p /home/gradle/.gradle && chmod -R 777 /home/gradle/.gradle

# Ejecuta el comando para construir el JAR
RUN gradle clean build --no-daemon

# Usa una imagen base de Java para ejecutar la aplicación
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el JAR generado desde la etapa de construcción
COPY --from=build /home/gradle/project/build/libs/user-api-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que corre tu aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]