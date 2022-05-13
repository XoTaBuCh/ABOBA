package com.tabletka.repository;

import com.tabletka.model.medicine.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> findByNameContaining(String name);
    Medicine findMedicineById(Long id);
}
