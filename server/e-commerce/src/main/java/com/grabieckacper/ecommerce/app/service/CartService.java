package com.grabieckacper.ecommerce.app.service;

import com.grabieckacper.ecommerce.app.model.Cart;
import com.grabieckacper.ecommerce.app.model.CartProduct;
import com.grabieckacper.ecommerce.app.model.Customer;
import com.grabieckacper.ecommerce.app.repository.CustomerRepository;
import com.grabieckacper.ecommerce.shared.exception.UnauthorizedException;
import com.grabieckacper.ecommerce.shared.model.Product;
import com.grabieckacper.ecommerce.shared.repository.CartProductRepository;
import com.grabieckacper.ecommerce.shared.repository.ProductRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    private final CartProductRepository cartProductRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public CartService(
            CartProductRepository cartProductRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository
    ) {
        this.cartProductRepository = cartProductRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    private Customer getCurrentCustomer() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if (authentication == null) {
            throw new UnauthorizedException();
        }

        String email = authentication.getName();

        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));
    }

    @Transactional
    public Cart getCart() {
        Customer customer = getCurrentCustomer();

        return customer.getCart();
    }

    @Transactional
    public void addProductToCart(Long productId) {
        Customer customer = getCurrentCustomer();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + productId + " not found"));

        Cart cart = customer.getCart();

        cartProductRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .ifPresent(_ -> {
                    throw new EntityExistsException("Product with id: " + productId + " already in cart");
                });

        CartProduct cartProduct = new CartProduct();
        cartProduct.setQuantity(1);
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);

        cart.addProductToCart(cartProduct);
        product.addProductToCart(cartProduct);

        cartProductRepository.save(cartProduct);
    }

    @Transactional
    public void updateProductQuantity(Long productId, Integer quantity) {
        Customer customer = getCurrentCustomer();
        Cart cart = customer.getCart();
        CartProduct cartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + productId + "is not in the cart"));

        cartProduct.setQuantity(quantity);

        cartProductRepository.save(cartProduct);
    }

    @Transactional
    public void removeProductFromCart(Long productId) {
        Customer customer = getCurrentCustomer();
        Cart cart = customer.getCart();
        CartProduct cartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + productId + "is not in the cart"));
        Product product = cartProduct.getProduct();

        cart.removeProductFromCart(cartProduct);
        product.removeProductFromCart(cartProduct);
        cartProduct.setCart(null);
        cartProduct.setProduct(null);

        cartProductRepository.delete(cartProduct);
    }
}
