package com.grabieckacper.ecommerce.dashboard.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CreateProductRequest(
        @NotBlank
        @NotNull
        String name,
        @NotBlank
        @NotNull
        String description,
        @NotNull
        @DecimalMin(value = "0.00", inclusive = false)
        @Digits(integer = 6, fraction = 2)
        BigDecimal price,
        @NotNull
        List<Long> categoryIds
) {}
