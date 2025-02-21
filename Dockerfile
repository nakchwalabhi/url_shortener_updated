# Use Maven image for building the application
FROM maven:3.9.6-eclipse-temurin-23 AS build

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Use OpenJDK 23 Slim for running the built application
FROM openjdk:23-slim

# Set working directory
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Run the application
CMD ["java", "-jar", "app.jar"]
