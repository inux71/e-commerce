package com.grabieckacper.ecommerce.app.repository;

import com.grabieckacper.ecommerce.app.model.Country;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends ListCrudRepository<Country, Long> {

}
