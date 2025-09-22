package com.grabieckacper.ecommerce.shared.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Category")
public class Category extends BaseEntity {
    @NotBlank
    @NotNull
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products =  new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}
