package com.grabieckacper.ecommerce.shared.repository;

import com.grabieckacper.ecommerce.shared.model.City;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends ListCrudRepository<City, Long> {
    List<City> findAllByCountry_Id(Long countryId);
}
