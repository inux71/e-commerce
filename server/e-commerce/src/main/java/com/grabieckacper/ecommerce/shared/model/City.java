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

    @OneToMany(mappedBy = "city")
    private Set<Address> addresses = new HashSet<>();

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }
}
