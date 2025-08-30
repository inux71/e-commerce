package com.grabieckacper.ecommerce.shared.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(@Email @NotNull String email, @NotBlank @Size(min = 8) String password) {}
