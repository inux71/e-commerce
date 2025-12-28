package com.grabieckacper.ecommerce.app.repository;

import com.grabieckacper.ecommerce.app.model.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {

}
