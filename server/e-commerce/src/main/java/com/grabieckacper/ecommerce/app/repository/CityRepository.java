package com.grabieckacper.ecommerce.app.repository;

import com.grabieckacper.ecommerce.app.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {
    
}
