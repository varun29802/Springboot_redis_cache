package com.spring.rediscache.controller;

import com.spring.rediscache.entity.CityTemperature;
import com.spring.rediscache.service.CityTemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/weather")
public class CityTemperatureController {
    @Autowired
    private CityTemperatureService cityTemperatureService;

    // Create or Update city temperature
    @PostMapping
    public ResponseEntity<CityTemperature> createOrUpdateCityTemperature(@RequestBody CityTemperature cityTemperature) {
        CityTemperature savedCityTemperature = cityTemperatureService.saveOrUpdateCityTemperature(cityTemperature);
        return new ResponseEntity<>(savedCityTemperature, HttpStatus.CREATED);
    }

    // Update city temperature by city name
    @PutMapping("/{city}")
    public ResponseEntity<CityTemperature> updateCityTemperature(@PathVariable String city, @RequestBody CityTemperature updatedTemperature) {
        Optional<CityTemperature> cityTemperature = cityTemperatureService.getCityTemperatureByCity(city);

        if (cityTemperature.isPresent()) {
            CityTemperature existingCityTemperature = cityTemperature.get();
            existingCityTemperature.setTemperature(updatedTemperature.getTemperature());
            CityTemperature savedCityTemperature = cityTemperatureService.saveOrUpdateCityTemperature(existingCityTemperature);
            return new ResponseEntity<>(savedCityTemperature, HttpStatus.OK);
        }

        // If city not found, return 404 (Not Found)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Get all city temperatures
    @GetMapping
    public ResponseEntity<List<CityTemperature>> getAllCityTemperatures() {
        List<CityTemperature> temperatures = cityTemperatureService.getAllCityTemperatures();
        return new ResponseEntity<>(temperatures, HttpStatus.OK);
    }

    // Get city temperature by city name
    @GetMapping("/{city}")
    public ResponseEntity<CityTemperature> getCityTemperature(@PathVariable String city) {
        Optional<CityTemperature> cityTemperature = cityTemperatureService.getCityTemperatureByCity(city);
        return cityTemperature.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Delete city temperature by city name
    @DeleteMapping("/{city}")
    public ResponseEntity<Void> deleteCityTemperature(@PathVariable String city) {
        cityTemperatureService.deleteCityTemperature(city);
        return ResponseEntity.noContent().build();
    }
}
