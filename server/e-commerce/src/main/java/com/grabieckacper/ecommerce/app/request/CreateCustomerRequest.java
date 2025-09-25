package com.grabieckacper.ecommerce.app.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCustomerRequest(
        @Email
        @NotNull
        String email,

        @NotBlank
        @Size(min = 8)
        String password,

        @NotBlank
        @NotNull
        String firstName,

        @NotBlank
        @NotNull
        String lastName
) {}
