package com.grabieckacper.ecommerce.app.controller;

import com.grabieckacper.ecommerce.app.model.Country;
import com.grabieckacper.ecommerce.app.response.CountryResponse;
import com.grabieckacper.ecommerce.app.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/app/country")
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<Iterable<CountryResponse>> getCountries() {
        List<Country> countries = countryService.getCountries();
        List<CountryResponse> response = countries.stream()
                .map(country -> new CountryResponse(
                        country.getId(),
                        country.getName(),
                        country.getIso2()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }
}
