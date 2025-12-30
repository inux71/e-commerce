package com.grabieckacper.ecommerce.app.service;

import com.grabieckacper.ecommerce.app.model.Customer;
import com.grabieckacper.ecommerce.app.repository.CustomerRepository;
import com.grabieckacper.ecommerce.shared.exception.UnauthorizedException;
import com.grabieckacper.ecommerce.shared.model.Address;
import com.grabieckacper.ecommerce.shared.repository.AddressRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    public List<Address> getCustomerAddresses() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if (authentication == null) {
            throw new UnauthorizedException();
        }

        String email = authentication.getName();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));

        return customer.getAddresses();
    }
}
