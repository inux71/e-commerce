package com.grabieckacper.ecommerce.shared.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "File")
public class File extends BaseEntity {
    @NotBlank
    @NotNull
    private String path;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String mimeType;

    @NotBlank
    @NotNull
    private String hash;

    @ManyToMany(mappedBy = "files")
    private List<Product> products =  new ArrayList<>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public List<Product> getProducts() {
        return products;
    }
}
