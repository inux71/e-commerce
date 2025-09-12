package com.grabieckacper.ecommerce.dashboard.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCategoryRequest(@NotBlank @NotNull String name) {}
