package com.grabieckacper.ecommerce.shared.controller;

import com.grabieckacper.ecommerce.shared.model.City;
import com.grabieckacper.ecommerce.shared.response.CityResponse;
import com.grabieckacper.ecommerce.shared.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/city")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<Iterable<CityResponse>> getCitiesByCountry(@PathVariable Long countryId) {
        List<City> cities = cityService.getCitiesByCountry(countryId);
        List<CityResponse> response = cities.stream()
                .map(city -> new CityResponse(
                        city.getId(),
                        city.getName()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }
}
