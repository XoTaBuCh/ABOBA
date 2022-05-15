package com.tabletka.service;

import com.tabletka.model.pharmacy.Pharmacy;
import com.tabletka.model.product.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductsForPharmacy(Pharmacy pharmacy);
}
