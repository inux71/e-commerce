package com.grabieckacper.ecommerce.app.model;

import com.grabieckacper.ecommerce.shared.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cart")
public class Cart extends BaseEntity {
    @OneToOne(mappedBy = "cart")
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
