package com.grabieckacper.ecommerce.app.model;

import com.grabieckacper.ecommerce.shared.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Cart")
public class Cart extends BaseEntity {
    @OneToOne(mappedBy = "cart")
    private Customer customer;

    @OneToMany(mappedBy = "cart")
    private Set<CartProduct> cartProducts = new HashSet<>();

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void addProductToCart(CartProduct product) {
        cartProducts.add(product);
        product.setCart(this);
    }

    public void removeProductFromCart(CartProduct cartProduct) {
        cartProducts.remove(cartProduct);
    }
}
