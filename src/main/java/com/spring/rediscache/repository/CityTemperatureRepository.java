package com.spring.rediscache.repository;

import com.spring.rediscache.entity.CityTemperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityTemperatureRepository extends JpaRepository<CityTemperature, String> {
    // JpaRepository provides CRUD methods automatically
    Optional<CityTemperature> findByCity(String city);

    // Custom method to delete CityTemperature by city
    void deleteByCity(String city);
}