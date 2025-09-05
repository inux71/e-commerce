package com.grabieckacper.ecommerce.shared.response;

import com.grabieckacper.ecommerce.shared.model.Product;

import java.util.List;

public record CategoryResponse(
        String name,
        List<Product> products
) {}
