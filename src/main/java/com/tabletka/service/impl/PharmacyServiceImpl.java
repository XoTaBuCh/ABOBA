package com.tabletka.service.impl;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.model.apothecary.Apothecary;
import com.tabletka.model.pharmacy.Pharmacy;
import com.tabletka.repository.PharmacyRepository;
import com.tabletka.security.AuthContextHandler;
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
    private final OrderServiceImpl orderServiceImpl;


    @Override
    public List<Pair<Pharmacy, Long>> getPharmaciesForApothecary() throws UserIsNotLoggedInException {
        Apothecary apothecary = ((Apothecary) authContextHandler.getLoggedInUser());
        List<Pair<Pharmacy, Long>> pharmaciesWithOrders = new ArrayList<>();
        List<Pharmacy> pharmacies = pharmacyRepository.findPharmaciesByApothecary(apothecary);
        for (Pharmacy pharmacy : pharmacies) {
            pharmaciesWithOrders.add(Pair.of(pharmacy, (long) orderServiceImpl.getActiveOrdersForPharmacy(pharmacy).size()));
        }
        if (pharmaciesWithOrders.isEmpty()) return null;
        return pharmaciesWithOrders;
    }

}
