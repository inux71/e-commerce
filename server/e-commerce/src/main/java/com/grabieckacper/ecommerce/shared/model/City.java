package com.grabieckacper.ecommerce.shared.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Immutable;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "City")
@Immutable
public class City extends BaseEntity {
    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }
}
