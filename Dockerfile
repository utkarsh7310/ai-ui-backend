FROM maven:3.9-eclipse-temurin-21
WORKDIR /app
COPY . .
# Using standard Maven instead of the wrapper
RUN mvn clean package -DskipTests
EXPOSE 8080
CMD ["sh", "-c", "java -jar target/*.jar"]
