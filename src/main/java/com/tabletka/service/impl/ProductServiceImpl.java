package com.tabletka.service.impl;

import com.tabletka.model.pharmacy.Pharmacy;
import com.tabletka.model.product.Product;
import com.tabletka.repository.ProductRepository;
import com.tabletka.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getProductsForPharmacy(Pharmacy pharmacy) {
        return productRepository.findProductsByPharmacy(pharmacy);
    }
}
