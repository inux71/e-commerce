package com.grabieckacper.ecommerce.app.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAddressRequest(
        @NotNull
        @NotBlank
        String postalCode,

        @NotNull
        Long cityId,

        @NotNull
        @NotBlank
        String street
) {}
