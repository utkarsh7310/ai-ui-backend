FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY . .
# Make maven wrapper executable
RUN chmod +x mvnw
# Build the Spring Boot app
RUN ./mvnw clean package -DskipTests
# Run the application
EXPOSE 8080
CMD ["sh", "-c", "java -jar target/*.jar"]
