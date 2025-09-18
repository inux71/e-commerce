package com.grabieckacper.ecommerce.shared.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(@NotBlank @NotNull @Size(min = 8) String password) {}
