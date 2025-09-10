package com.grabieckacper.ecommerce.dashboard.controller;

import com.grabieckacper.ecommerce.dashboard.response.ProductResponse;
import com.grabieckacper.ecommerce.shared.model.Category;
import com.grabieckacper.ecommerce.shared.model.File;
import com.grabieckacper.ecommerce.shared.model.Product;
import com.grabieckacper.ecommerce.shared.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAll(Pageable pageable) {
        Page<Product> products = productService.getAll(pageable);
        Page<ProductResponse> response = products.map(product -> new ProductResponse(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getCreatedBy(),
                product.getUpdatedBy(),
                product.getCategories()
                        .stream()
                        .map(Category::getName)
                        .toList(),
                product.getFiles()
                        .stream()
                        .map(File::getId)
                        .toList()
        ));

        return ResponseEntity.ok(response);
    }
}
