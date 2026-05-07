# Etapa 1: Construcción (Build) con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos solo el pom.xml primero para aprovechar el caché de Docker
# y descargar las dependencias sin tener que volver a hacerlo en cada cambio de código
COPY pom.xml .
RUN mvn dependency:go-offline

# Ahora copiamos el código fuente y empaquetamos
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (Runtime) con un JRE ligero
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos el .jar generado desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto de Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
