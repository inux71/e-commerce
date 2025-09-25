package com.grabieckacper.ecommerce.app.repository;

import com.grabieckacper.ecommerce.app.model.Customer;
import com.grabieckacper.ecommerce.shared.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends UserRepository<Customer, Long> {

}
