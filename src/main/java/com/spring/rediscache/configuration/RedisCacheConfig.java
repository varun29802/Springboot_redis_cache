package com.spring.rediscache.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

/**
 * Configuration class for setting up Redis Cache with Spring.
 *
 * This class contains the configuration needed to enable caching in a Spring application
 * using Redis as the caching provider. The cache settings, such as the default Time-to-Live (TTL)
 * for cached values and the behavior when caching null values, are defined here.
 *
 * @EnableCaching annotation enables Spring's caching abstraction. It makes it possible to use
 * annotations like @Cacheable and @CachePut for caching in the application.
 */
@Configuration
@EnableCaching
public class RedisCacheConfig {

    // Logger instance for logging events and debugging information
    private static final Logger logger = LoggerFactory.getLogger(RedisCacheConfig.class);

    /**
     * Defines the RedisCacheConfiguration bean which configures TTL and other cache settings.
     *
     * This configuration sets the default Time-to-Live (TTL) for cache entries to 1 minute and
     * disables caching of null values. The TTL defines how long a cached item remains in the cache
     * before it expires.
     *
     * @return the RedisCacheConfiguration with TTL and caching settings.
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        // Log to verify if the method is being executed
        logger.info("Redis Cache Configuration is being applied with TTL set to 1 minute.");

        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(1))  // TTL set to 1 minute
                .disableCachingNullValues();      // Optional: Disabling caching of null values
    }

    /**
     * Defines the RedisCacheManager bean that integrates with Redis to manage the caching operations.
     *
     * The RedisCacheManager is responsible for managing the cache, including cache entry creation,
     * retrieval, and invalidation. This bean uses the configuration defined above and connects
     * to Redis through the RedisConnectionFactory.
     *
     * @param redisConnectionFactory RedisConnectionFactory used for establishing a connection to Redis.
     * @return a RedisCacheManager that handles caching operations.
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration()) // Use the configuration created above
                .build();
    }
}
