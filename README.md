# Spring Boot Redis Caching Example

This project demonstrates a Spring Boot application that integrates Redis caching for improved performance and reduced database load. It uses Spring's caching annotations to seamlessly connect with Redis for efficient data retrieval, and integrates PostgreSQL as the database.

## Features:
- Spring Boot application with Redis caching integration.
- Docker Compose setup for Redis service.
- Easy setup for Redis CLI installation.
- PostgreSQL database integration.

## Setup Instructions:

### 1. **Clone the repository**:
   Clone the repository to your local machine, or if you already have it, pull the latest changes:

   ```bash
   git pull
   ```

### 2. **Start Redis with Docker Compose**:
   Run the following command to start Redis in a container using Docker Compose:

   ```bash
   docker-compose up -d
   ```

   This will start Redis on port `6379` in a container named `redis-cache`.

### 3. **Install Redis CLI**:
   You will need the Redis CLI to interact with the Redis server. Install the Redis CLI by downloading it from the link below:

   [Download Redis CLI](https://github.com/MicrosoftArchive/redis/releases)  
   (Version: `Redis-x64-3.0.504.msi`)

   After installing, you can connect to the Redis server by running:

   ```bash
   redis-cli -h localhost -p 6379
   ```

### 4. **Configure PostgreSQL**:
   Check the PostgreSQL database configuration in the `application.properties` (or `application.yml`) file, located in the `src/main/resources` directory. Update the connection URL according to your environment:

   ```properties
   spring.datasource.url=jdbc:postgresql://<your-db-host>:<port>/<your-database-name>
   spring.datasource.username=<your-db-username>
   spring.datasource.password=<your-db-password>
   ```

   Once configured, create your PostgreSQL database if it doesn't exist already:

   ```bash
   psql -U <your-db-username> -h <your-db-host> -p <port> -c "CREATE DATABASE <your-database-name>;"
   ```

### 5. **Run the Spring Boot Application**:
   Once Redis and PostgreSQL are up and running, start the Spring Boot application as usual:

   ```bash
   ./mvnw spring-boot:run
   ```

   The application will start running, and Redis caching will be used for certain data operations.

### 6. **Access Redis**:
   You can now interact with Redis using the Redis CLI or directly through the Spring Boot application. To check if Redis is storing data correctly, you can use commands like:

   ```bash
   redis-cli GET <your-key>
   ```

---

## Additional Notes:

- **Redis Caching**: The application uses Spring’s caching abstraction and integrates with Redis. You can customize caching behavior by annotating methods with `@Cacheable`, `@CacheEvict`, or `@CachePut` to control how data is cached and retrieved.

- **PostgreSQL Integration**: The PostgreSQL connection is managed via Spring Data JPA, so you can interact with the database using repositories and entities as usual.

- **Docker Compose**: The Docker Compose file automatically configures Redis for you, so you don’t need to manually set up Redis.

---

## Troubleshooting:

- **Redis Connection Issues**: Ensure that Redis is running on port `6379` by checking with `docker ps`. If it's not running, restart Redis using `docker-compose up -d`.
  
- **Database Connection Issues**: Double-check the PostgreSQL connection URL and ensure the database is created correctly.

---

