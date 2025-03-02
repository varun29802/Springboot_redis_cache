package com.spring.rediscache.service;

import com.spring.rediscache.entity.CityTemperature;
import com.spring.rediscache.repository.CityTemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CityTemperatureService {

    @Autowired
    private CityTemperatureRepository cityTemperatureRepository;

    // Create or Update city temperature
    @CachePut(value = "temperatureCache", key = "#cityTemperature.city")
    public CityTemperature saveOrUpdateCityTemperature(CityTemperature cityTemperature) {
        return cityTemperatureRepository.save(cityTemperature);
    }

    public List<CityTemperature> getAllCityTemperatures() {
        return cityTemperatureRepository.findAll();
    }

    // Get city temperature by city name
    @Cacheable(value = "temperatureCache", key = "#city")
    public Optional<CityTemperature> getCityTemperatureByCity(String city) {
        System.out.println("Getting data from DB");
        return cityTemperatureRepository.findByCity(city);
    }

    @Transactional
    // Delete city temperature by city name
    @CacheEvict(value = "temperatureCache", key = "#city")
    public void deleteCityTemperature(String city) {
        cityTemperatureRepository.deleteByCity(city);
    }
}