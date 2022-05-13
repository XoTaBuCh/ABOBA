package com.tabletka.service.impl;

import com.tabletka.dto.MedicineDTO;
import com.tabletka.mapper.MedicineMapper;
import com.tabletka.model.medicine.Medicine;
import com.tabletka.model.product.Product;
import com.tabletka.repository.MedicineRepository;
import com.tabletka.repository.ProductRepository;
import com.tabletka.service.MedicineService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class MedicineServiceImpl implements MedicineService {
    private final MedicineRepository medicineRepository;
    private final MedicineMapper medicineMapper;

    @Override
    public List<MedicineDTO> getMedicines(String name) {
        List<Medicine> medicines =  medicineRepository.findByNameContaining(name);
        List<MedicineDTO> newMedicines = new ArrayList<>();
        for (Medicine medicine: medicines) {
            List<Product> products = medicine.getProducts();

            MedicineDTO medicineDTO = medicineMapper.toDTO(medicine);

            if (!products.isEmpty()){
                medicineDTO.setMax(Collections.max(products, Comparator.comparing(Product::getPrice)).getPrice());
                medicineDTO.setMin(Collections.min(products, Comparator.comparing(Product::getPrice)).getPrice());
            }
            newMedicines.add(medicineDTO);
        }
        return newMedicines;
    }
}
