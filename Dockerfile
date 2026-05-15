# Build with JDK
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Run with JRE only (smaller, less memory)
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["sh", "-c", "java -Xms64m -Xmx300m -jar app.jar"]