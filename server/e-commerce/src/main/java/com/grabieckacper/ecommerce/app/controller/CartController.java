package com.grabieckacper.ecommerce.app.controller;

import com.grabieckacper.ecommerce.app.model.Cart;
import com.grabieckacper.ecommerce.app.request.UpdateCartProductQuantityRequest;
import com.grabieckacper.ecommerce.app.response.CartProductResponse;
import com.grabieckacper.ecommerce.app.response.CartResponse;
import com.grabieckacper.ecommerce.app.response.ProductResponse;
import com.grabieckacper.ecommerce.app.service.CartService;
import com.grabieckacper.ecommerce.shared.model.Product;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/app/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart() {
        Cart cart = cartService.getCart();
        CartResponse cartResponse = new CartResponse(
                cart.getCartProducts()
                        .stream()
                        .map(cartProduct -> {
                            Product product = cartProduct.getProduct();

                            return new CartProductResponse(
                                    cartProduct.getId(),
                                    new ProductResponse(
                                            product.getId(),
                                            product.getName(),
                                            product.getDescription(),
                                            product.getPrice()
                                    ),
                                    cartProduct.getQuantity()
                            );
                        })
                        .toList()
        );

        return ResponseEntity.ok(cartResponse);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addProductToCart(@PathVariable Long productId) {
        cartService.addProductToCart(productId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Void> updateProductQuantity(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartProductQuantityRequest updateCartProductQuantityRequest
    ) {
        cartService.updateProductQuantity(productId, updateCartProductQuantityRequest.quantity());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long productId) {
        cartService.removeProductFromCart(productId);

        return ResponseEntity.noContent().build();
    }
}
