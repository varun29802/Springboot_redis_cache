package com.spring.rediscache.service;

import com.spring.rediscache.entity.CityTemperature;
import com.spring.rediscache.repository.CityTemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing city temperatures and interacting with the database.
 *
 * This service provides methods to save, update, retrieve, and delete city temperature data.
 * It also leverages Spring's caching mechanism to improve performance by reducing
 * unnecessary database queries.
 *
 * Caching is used for:
 * - Storing temperature data of individual cities.
 * - Storing a list of all city temperatures.
 * - Evicting or updating the cache when data is modified.
 */
@Service
public class CityTemperatureService {

    // Injecting the repository for database interactions
    @Autowired
    private CityTemperatureRepository cityTemperatureRepository;

    // Injecting the CacheManager to interact with the caching mechanism
    @Autowired
    private CacheManager cacheManager;

    /**
     * Creates or updates a city temperature entry in the database.
     *
     * This method uses @CachePut to update the cache with the newly saved or updated city
     * temperature. The temperatureCache is updated with the new city temperature, and
     * the allTemperatureCache is evicted to ensure the list of all city temperatures is refreshed.
     *
     * @param cityTemperature the city temperature entity to save or update.
     * @return the saved or updated city temperature entity.
     */
    @CachePut(value = "temperatureCache", key = "#cityTemperature.city")
    @CacheEvict(value = "allTemperatureCache", key = "'allCityTemperatures'")
    public CityTemperature saveOrUpdateCityTemperature(CityTemperature cityTemperature) {
        return cityTemperatureRepository.save(cityTemperature);
    }

    /**
     * Retrieves all city temperatures from the database.
     *
     * This method uses @Cacheable to store the list of all city temperatures in the cache
     * under the key 'allCityTemperatures'. If the cache contains this key, the data will
     * be returned from the cache instead of querying the database again.
     *
     * @return a list of all city temperatures.
     */
    @Cacheable(value = "allTemperatureCache", key = "'allCityTemperatures'")
    public List<CityTemperature> getAllCityTemperatures() {
        return cityTemperatureRepository.findAll();
    }

    /**
     * Retrieves the temperature of a specific city from the database.
     *
     * This method uses @Cacheable to store the temperature of the specific city in the cache.
     * If the temperature data for the city is already in the cache, it is returned from the cache.
     * Otherwise, the method queries the database for the data and stores it in the cache for future use.
     *
     * @param city the name of the city whose temperature is to be fetched.
     * @return an Optional containing the city temperature, or empty if the city is not found.
     */
    @Cacheable(value = "temperatureCache", key = "#city")
    public Optional<CityTemperature> getCityTemperatureByCity(String city) {
        System.out.println("Getting data from DB");
        return cityTemperatureRepository.findByCity(city);
    }

    /**
     * Deletes a city temperature entry from the database and evicts the related cache entries.
     *
     * This method evicts the specific city temperature from the cache and also evicts the
     * 'allCityTemperatures' cache to ensure the list of city temperatures is updated.
     * The city temperature is then deleted from the database.
     *
     * @param city the name of the city whose temperature data is to be deleted.
     */
    @Transactional
    public void deleteCityTemperature(String city) {
        // Evict city temperature and all temperatures cache
        cacheManager.getCache("temperatureCache").evict(city);
        cacheManager.getCache("allTemperatureCache").evict("allCityTemperatures");

        // Delete the city temperature from the database
        cityTemperatureRepository.deleteByCity(city);
    }
}
