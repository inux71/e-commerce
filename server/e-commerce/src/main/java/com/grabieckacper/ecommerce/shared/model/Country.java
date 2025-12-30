package com.grabieckacper.ecommerce.shared.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Immutable;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Country")
@Immutable
public class Country extends BaseEntity {
    @NotNull
    @Size(max = 100)
    private String name;

    @Size(min = 2, max = 2)
    private String iso2;

    @OneToMany(mappedBy = "country")
    private Set<City> cities = new HashSet<>();

    public String getName() {
        return name;
    }

    public String getIso2() {
        return iso2;
    }

    public Set<City> getCities() {
        return cities;
    }
}
