package com.tabletka.service;

import com.tabletka.dto.MedicineDTO;
import com.tabletka.model.medicine.Medicine;

import java.util.List;

public interface MedicineService {
    List<MedicineDTO> getMedicinesByName(String name);
    Medicine getMedicineById(Long id);

    List<Medicine> getAllMedicines();

    Medicine addMedicine(Medicine medicine);
}
