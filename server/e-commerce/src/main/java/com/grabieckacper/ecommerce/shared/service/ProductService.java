package com.grabieckacper.ecommerce.shared.service;

import com.grabieckacper.ecommerce.shared.model.Category;
import com.grabieckacper.ecommerce.shared.model.Product;
import com.grabieckacper.ecommerce.shared.repository.CategoryRepository;
import com.grabieckacper.ecommerce.shared.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    private void insert(Product product, String name, String description, BigDecimal price, List<Long> categoryIds) {
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        product.getCategories().clear();
        categoryIds.forEach(id -> {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));

            category.addProduct(product);
            product.addCategory(category);
        });

        productRepository.save(product);
    }

    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional
    public void save(String name, String description, BigDecimal price, List<Long> categoryIds) {
        insert(new Product(), name, description, price, categoryIds);
    }

    @Transactional
    public void update(Long id, String name, String description, BigDecimal price, List<Long> categoryIds) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));

        insert(product, name, description, price, categoryIds);
    }
}
