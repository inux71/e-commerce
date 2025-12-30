package com.grabieckacper.ecommerce.app.controller;

import com.grabieckacper.ecommerce.app.service.AddressService;
import com.grabieckacper.ecommerce.shared.model.Address;
import com.grabieckacper.ecommerce.shared.response.AddressResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/app/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<Iterable<AddressResponse>> getCustomerAddresses() {
        List<Address> addresses = addressService.getCustomerAddresses();
        List<AddressResponse> response = addresses.stream()
                .map(address -> new AddressResponse(
                        address.getId(),
                        address.getCity().getCountry().getName(),
                        address.getPostalCode(),
                        address.getCity().getName(),
                        address.getStreet()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }
}
