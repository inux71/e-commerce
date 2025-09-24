package com.grabieckacper.ecommerce.dashboard.controller;

import com.grabieckacper.ecommerce.dashboard.request.CreateProductRequest;
import com.grabieckacper.ecommerce.dashboard.request.UpdateProductRequest;
import com.grabieckacper.ecommerce.dashboard.response.ProductResponse;
import com.grabieckacper.ecommerce.shared.model.Category;
import com.grabieckacper.ecommerce.shared.model.Product;
import com.grabieckacper.ecommerce.shared.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                product.getId(),
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
                        .toList()
        ));

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
        productService.save(
                createProductRequest.name(),
                createProductRequest.description(),
                createProductRequest.price(),
                createProductRequest.categoryIds()
        );

        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Void> updateProduct(@Valid @RequestBody UpdateProductRequest updateProductRequest) {
        productService.update(
                updateProductRequest.id(),
                updateProductRequest.name(),
                updateProductRequest.description(),
                updateProductRequest.price(),
                updateProductRequest.categoryIds()
        );

        return ResponseEntity.noContent().build();
    }
}
