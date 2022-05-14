package com.tabletka.repository;

import com.tabletka.model.apothecary.Apothecary;
import com.tabletka.model.pharmacy.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    List<Pharmacy> findPharmaciesByApothecary(Apothecary apothecary);
    Pharmacy findPharmacyById(Long id);
}
