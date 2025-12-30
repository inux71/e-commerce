package com.grabieckacper.ecommerce.shared.model;

import com.grabieckacper.ecommerce.app.model.Customer;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Address")
public class Address extends BaseEntity {
    @NotNull
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @NotNull
    private String street;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
