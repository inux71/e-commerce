package com.grabieckacper.ecommerce.app.service;

import com.grabieckacper.ecommerce.app.model.Customer;
import com.grabieckacper.ecommerce.app.repository.CustomerRepository;
import com.grabieckacper.ecommerce.shared.exception.UnauthorizedException;
import com.grabieckacper.ecommerce.shared.model.Address;
import com.grabieckacper.ecommerce.shared.model.City;
import com.grabieckacper.ecommerce.shared.repository.AddressRepository;
import com.grabieckacper.ecommerce.shared.repository.CityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final CustomerRepository customerRepository;

    public AddressService(
            AddressRepository addressRepository, CityRepository cityRepository, CustomerRepository customerRepository
    ) {
        this.addressRepository = addressRepository;
        this.cityRepository = cityRepository;
        this.customerRepository = customerRepository;
    }

    private Customer getCustomer() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if (authentication == null) {
            throw new UnauthorizedException();
        }

        String email = authentication.getName();

        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));
    }

    public List<Address> getCustomerAddresses() {
        Customer customer = getCustomer();

        return customer.getAddresses();
    }

    @Transactional
    public void createAddress(String postalCode, Long cityId, String street) {
        Customer customer = getCustomer();
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException("City not found"));

        Address address = new Address();
        address.setPostalCode(postalCode);
        address.setCity(city);
        address.setStreet(street);

        customer.addAddress(address);
        address.setCustomer(customer);

        addressRepository.save(address);
    }

    @Transactional
    public void removeAddress(Long id) {
        Customer customer = getCustomer();
        Address address = addressRepository.findByIdAndCustomer_Id(id, customer.getId())
                .orElseThrow(() -> new EntityNotFoundException("Address with id: " + id + " not found"));

        customer.removeAddress(address);
        address.setCustomer(null);

        addressRepository.delete(address);
    }
}
