package com.grabieckacper.ecommerce.app.controller;

import com.grabieckacper.ecommerce.app.request.CreateCustomerRequest;
import com.grabieckacper.ecommerce.app.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
        customerService.createCustomer(
                createCustomerRequest.email(),
                createCustomerRequest.password(),
                createCustomerRequest.firstName(),
                createCustomerRequest.lastName()
        );

        return ResponseEntity.noContent().build();
    }
}
