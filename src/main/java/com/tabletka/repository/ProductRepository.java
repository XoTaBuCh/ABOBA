package com.tabletka.repository;

import com.tabletka.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductById(Long productId);
}
