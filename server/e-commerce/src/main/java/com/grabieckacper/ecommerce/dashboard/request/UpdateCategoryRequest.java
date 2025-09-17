package com.grabieckacper.ecommerce.dashboard.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCategoryRequest(@NotNull Long id, @NotBlank @NotNull String name) {}
