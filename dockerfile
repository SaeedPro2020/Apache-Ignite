# Use an official OpenJDK runtime as a parent image
FROM openjdk:22-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the executable JAR file to the container
COPY target/apache-0.0.1-SNAPSHOT.jar /app/my-spring-boot-app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/my-spring-boot-app.jar"]

