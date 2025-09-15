package com.grabieckacper.ecommerce.shared.response;

public record CategoryResponse(
        Long id,
        String name,
        Integer numberOfProducts
) {}
