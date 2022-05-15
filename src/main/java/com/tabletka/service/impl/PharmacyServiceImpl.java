package com.tabletka.service.impl;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.model.apothecary.Apothecary;
import com.tabletka.model.medicine.Medicine;
import com.tabletka.model.pharmacy.Pharmacy;
import com.tabletka.model.product.Product;
import com.tabletka.repository.MedicineRepository;
import com.tabletka.repository.PharmacyRepository;
import com.tabletka.repository.ProductRepository;
import com.tabletka.security.AuthContextHandler;
import com.tabletka.service.OrderService;
import com.tabletka.service.PharmacyService;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private final AuthContextHandler authContextHandler;
    private final OrderService orderService;
    private final ProductRepository productRepository;
    private final MedicineRepository medicineRepository;



    @Override
    public List<Pair<Pharmacy, Long>> getPharmaciesForApothecary() throws UserIsNotLoggedInException {
        Apothecary apothecary = ((Apothecary) authContextHandler.getLoggedInUser());
        List<Pair<Pharmacy, Long>> pharmaciesWithOrders = new ArrayList<>();
        List<Pharmacy> pharmacies = pharmacyRepository.findPharmaciesByApothecary(apothecary);
        for (Pharmacy pharmacy : pharmacies) {
            pharmaciesWithOrders.add(Pair.of(pharmacy, (long) orderService.getActiveOrdersForPharmacy(pharmacy).size()));
        }
        if (pharmaciesWithOrders.isEmpty()) return null;
        return pharmaciesWithOrders;
    }

    @Override
    public void addPharmacy(Pharmacy pharmacy) throws UserIsNotLoggedInException {
        Apothecary apothecary = ((Apothecary) authContextHandler.getLoggedInUser());
        pharmacy.setApothecary(apothecary);
        System.out.println(pharmacy.getId());
        pharmacyRepository.save(pharmacy);
    }

    @Override
    public Pharmacy getPharmacyById(Long id) {
        return pharmacyRepository.findPharmacyById(id);
    }

    @Override
    public void changeProductInfo(Long id, Long amount, Double price) {
        Product product = productRepository.findProductById(id);
        product.setAmount(amount);
        product.setPrice(price);

        productRepository.save(product);
    }

    @Override
    public void addProduct(Long pharmacyId, Long medicineId, Long amount, Double price) {
        Product product = new Product();
        product.setAmount(amount);
        product.setPrice(price);
        product.setPharmacy(pharmacyRepository.findPharmacyById(pharmacyId));
        product.setMedicine(medicineRepository.findMedicineById(medicineId));

        productRepository.save(product);
    }

}
