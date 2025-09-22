package com.grabieckacper.ecommerce.shared.repository;

import com.grabieckacper.ecommerce.shared.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

}
