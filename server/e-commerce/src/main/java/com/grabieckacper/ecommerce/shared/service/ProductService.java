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

    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional
    public void save(String name, String description, BigDecimal price, List<Long> categoryIds) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        categoryIds.forEach(id -> {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));

            category.addProduct(product);
            product.addCategory(category);
        });

        productRepository.save(product);
    }
}
