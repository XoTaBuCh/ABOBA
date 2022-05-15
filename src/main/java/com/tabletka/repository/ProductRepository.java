package com.tabletka.repository;

import com.tabletka.model.pharmacy.Pharmacy;
import com.tabletka.model.product.Product;
import org.hibernate.resource.jdbc.spi.PhysicalConnectionHandlingMode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductById(Long productId);
    List<Product> findProductsByPharmacy(Pharmacy pharmacy);
}
