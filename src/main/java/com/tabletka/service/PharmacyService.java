package com.tabletka.service;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.model.apothecary.Apothecary;
import com.tabletka.model.pharmacy.Pharmacy;
import org.springframework.data.util.Pair;

import java.util.List;

public interface PharmacyService {
    List<Pair<Pharmacy, Long>> getPharmaciesForApothecary() throws UserIsNotLoggedInException;
}
