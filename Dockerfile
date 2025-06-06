# Usa una imagen base con OpenJDK 17
FROM openjdk:17-jdk-slim

# Crea un directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR al contenedor
COPY target/movigestion-api-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8080, que es el puerto por defecto de Spring Boot
EXPOSE 8080

# Variables de entorno opcionales para el contenedor
ENV JAVA_OPTS=""

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
