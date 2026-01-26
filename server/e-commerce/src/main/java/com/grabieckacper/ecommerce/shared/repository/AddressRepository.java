package com.grabieckacper.ecommerce.shared.repository;

import com.grabieckacper.ecommerce.shared.model.Address;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends ListCrudRepository<Address, Long> {
    Optional<Address> findByIdAndCustomer_Id(Long id, Long customerId);
}
