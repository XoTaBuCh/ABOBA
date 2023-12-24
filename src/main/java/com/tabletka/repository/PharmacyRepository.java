package com.tabletka.repository;

import com.tabletka.model.apothecaryAdmin.ApothecaryAdmin;
import com.tabletka.model.pharmacy.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    List<Pharmacy> findAllByApothecaryAdmin(ApothecaryAdmin apothecaryAdmin);
    Pharmacy findPharmacyById(Long id);
}
