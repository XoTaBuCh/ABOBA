package com.tabletka.service;

import com.tabletka.dto.MedicineDTO;

import java.util.List;

public interface MedicineService {
    List<MedicineDTO> getMedicines(String name);
}
