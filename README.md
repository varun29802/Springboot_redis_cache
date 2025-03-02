# Spring Boot Redis Caching Example

This project demonstrates a Spring Boot application that integrates Redis caching for improved performance and reduced database load. It utilizes Spring's caching annotations to seamlessly connect with Redis for efficient data retrieval.

## Features:
- Spring Boot application with Redis caching integration.
- Docker Compose setup for Redis service.
- Easy setup for Redis CLI installation.

## Setup Instructions:

1. **Clone the repository**:
   Clone the repository to your local machine.

2. **Start Redis with Docker Compose**:
   Run the following command to start Redis in a container using Docker Compose:

   ```bash
   docker-compose up -d
   ```

   This will start Redis on port `6379` in a container named `redis-cache`.

3. **Install Redis CLI**:
   You will need the Redis CLI to interact with the Redis server. Install the Redis CLI by downloading it from the link below:
   
   [Download Redis CLI](https://github.com/MicrosoftArchive/redis/releases)  
   (Version: `Redis-x64-3.0.504.msi`)

4. **Run the Spring Boot Application**:
   Once Redis is up and running, start the Spring Boot application as usual:
   
   ```bash
   ./mvnw spring-boot:run
   ```

5. **Access Redis**:
   You can now interact with Redis using the Redis CLI or directly through the Spring Boot application.

---
