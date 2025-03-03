# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim AS build

# Set the working directory in the container
WORKDIR /redis_app

# Copy the JAR file from your local machine (target directory) into the container
COPY target/rediscache-0.0.1-SNAPSHOT.jar /redis_app/rediscache.jar

# Expose the port your Spring Boot app will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "rediscache.jar"]