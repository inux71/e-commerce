package com.grabieckacper.ecommerce.app.model;

import com.grabieckacper.ecommerce.shared.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Immutable;

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
