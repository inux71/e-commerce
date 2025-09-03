package com.grabieckacper.ecommerce.shared.service;

import com.grabieckacper.ecommerce.shared.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
