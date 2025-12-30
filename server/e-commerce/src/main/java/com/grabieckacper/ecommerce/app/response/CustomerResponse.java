package com.grabieckacper.ecommerce.app.response;

public record CustomerResponse(
        Long id,
        String email,
        Integer addresses
) {}
