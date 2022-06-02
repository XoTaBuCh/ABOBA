package com.tabletka.service;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.model.apothecary.Apothecary;
import com.tabletka.model.medicine.Medicine;
import com.tabletka.model.order.Order;
import com.tabletka.model.pharmacy.Pharmacy;
import org.springframework.data.util.Pair;

import java.util.List;

public interface PharmacyService {
    List<Pair<Pharmacy, Long>> getPharmaciesForApothecary() throws UserIsNotLoggedInException;

    void addPharmacy(Pharmacy pharmacy) throws UserIsNotLoggedInException;

    void addPharmacy(String name, String address, Long apothecaryId);

    Pharmacy getPharmacyById(Long id);

    void changeProductInfo(Long id, Long amount, Double price);

    void addProduct(Long pharmacyId, Long medicineId, Long amount, Double price);

    List<Order> getPharmacyOrders(Long phId);

    void changePharmacyNameAddress(String name, String address, Long pharmacyId);

    List<Pharmacy> getPharmacies();
}
