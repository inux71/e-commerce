package com.grabieckacper.ecommerce.dashboard.controller;

import com.grabieckacper.ecommerce.dashboard.request.CreateCategoryRequest;
import com.grabieckacper.ecommerce.shared.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "dashboard-category-controller")
@RequestMapping("/api/dashboard/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Void> createCategory(
            @Valid @RequestBody CreateCategoryRequest createCategoryRequest
    ) {
        categoryService.saveCategory(createCategoryRequest.name());

        return ResponseEntity.noContent().build();
    }
}
