package com.grabieckacper.ecommerce.shared.controller;

import com.grabieckacper.ecommerce.shared.model.Category;
import com.grabieckacper.ecommerce.shared.response.CategoryResponse;
import com.grabieckacper.ecommerce.shared.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Iterable<CategoryResponse>> getCategories() {
        List<Category> categories = categoryService.getCategories();
        List<CategoryResponse> categoryResponse = categories.stream()
                .map(category -> new CategoryResponse(category.getName(), category.getProducts()))
                .toList();

        return ResponseEntity.ok(categoryResponse);
    }
}
