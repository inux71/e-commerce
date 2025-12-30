package com.grabieckacper.ecommerce.shared.response;

public record AddressResponse(
        Long id,
        String country,
        String postalCode,
        String city,
        String street
) {}
