# Use a base image with Java 17
FROM openjdk:17-alpine

# Set the working directory inside the container
WORKDIR /app

# Set the source label
LABEL org.opencontainers.image.source https://github.com/jcilacad/aeroplanner-rest-api

# Copy the env properties
COPY env.properties .

# Copy the jar file
COPY target/aeroplanner-rest-api*.jar .

# Expose port 8005
EXPOSE 8005

# Command to run the Spring Boot application
CMD ["java", "-jar", "aeroplanner-rest-api-1.0.0.jar"]
