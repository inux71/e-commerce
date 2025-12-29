package com.grabieckacper.ecommerce.app.service;

import com.grabieckacper.ecommerce.app.model.Country;
import com.grabieckacper.ecommerce.app.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getCountries() {
        return countryRepository.findAll();
    }
}
