package com.grabieckacper.ecommerce.shared.service;

import com.grabieckacper.ecommerce.shared.model.Category;
import com.grabieckacper.ecommerce.shared.repository.CategoryRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public void saveCategory(String name) {
        String preparedName = name.toLowerCase().trim();

        categoryRepository.findByName(preparedName)
                .ifPresent(_ -> {
                    throw new EntityExistsException("Category with name " + name + " already exists");
                });

        Category category = new Category();
        category.setName(preparedName);

        categoryRepository.save(category);
    }

    public void updateCategory(Long id, String name) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));

        category.setName(name.toLowerCase().trim());

        categoryRepository.save(category);
    }
}
