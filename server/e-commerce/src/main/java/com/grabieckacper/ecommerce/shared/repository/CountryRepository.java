package com.grabieckacper.ecommerce.shared.repository;

import com.grabieckacper.ecommerce.shared.model.Country;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends ListCrudRepository<Country, Long> {

}
