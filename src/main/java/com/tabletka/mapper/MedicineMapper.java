package com.tabletka.mapper;


import com.tabletka.dto.MedicineDTO;
import com.tabletka.model.medicine.Medicine;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicineMapper {
    Medicine toMedicine(MedicineDTO dto);

    MedicineDTO toDTO(Medicine medicine);
}