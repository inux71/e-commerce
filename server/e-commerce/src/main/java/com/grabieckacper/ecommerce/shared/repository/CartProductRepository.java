package com.grabieckacper.ecommerce.shared.repository;

import com.grabieckacper.ecommerce.app.model.CartProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartProductRepository extends CrudRepository<CartProduct, Long> {
    Optional<CartProduct> findByCartIdAndProductId(Long cartId, Long productId);
}
