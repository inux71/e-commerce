package com.grabieckacper.ecommerce.app.service;

import com.grabieckacper.ecommerce.app.model.City;
import com.grabieckacper.ecommerce.app.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getCitiesByCountry(Long countryId) {
        return cityRepository.findAllByCountry_Id(countryId);
    }
}
