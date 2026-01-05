package com.grabieckacper.ecommerce.app.controller;

import com.grabieckacper.ecommerce.app.request.CreateAddressRequest;
import com.grabieckacper.ecommerce.app.service.AddressService;
import com.grabieckacper.ecommerce.shared.model.Address;
import com.grabieckacper.ecommerce.shared.response.AddressResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Void> createAddress(@Valid @RequestBody CreateAddressRequest createAddressRequest) {
        addressService.createAddress(
                createAddressRequest.postalCode(),
                createAddressRequest.cityId(),
                createAddressRequest.street()
        );

        return ResponseEntity.noContent().build();
    }
}
