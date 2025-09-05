package com.grabieckacper.ecommerce.shared.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Category")
public class Category extends BaseEntity {
    @NotBlank
    @NotNull
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products =  new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }
}
