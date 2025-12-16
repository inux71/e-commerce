package com.grabieckacper.ecommerce.app.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateCartProductQuantityRequest(@NotNull @Min(value = 1) Integer quantity) {}
