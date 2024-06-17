# Use an official OpenJDK runtime as a parent image
FROM openjdk:22-jdk-slim

# Add a volume pointing to /tmp
VOLUME /tmp

# Copy the executable JAR file to the container
COPY ./libs/java-functional-1.0-SNAPSHOT.jar /app/libs/
COPY target/apache-0.0.1-SNAPSHOT.jar /app/my-spring-boot-app.jar

# Set the working directory
WORKDIR /app

# Expose port 8080
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","your-spring-boot-app.jar"]
