package com.grabieckacper.ecommerce.app.response;

public record CartProductResponse(
        Long id,
        ProductResponse product,
        Integer quantity
) {}
