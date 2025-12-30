package com.grabieckacper.ecommerce.shared.service;

import com.grabieckacper.ecommerce.shared.model.City;
import com.grabieckacper.ecommerce.shared.repository.CityRepository;
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
