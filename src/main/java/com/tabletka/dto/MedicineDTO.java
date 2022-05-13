package com.tabletka.dto;

import com.tabletka.model.medicine.MedicinesType;
import com.tabletka.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDTO {
    private Long id;
    private String name;
    private String description;
    private String fabricator;
    @Enumerated(value = EnumType.STRING)
    private MedicinesType medicinesType;
    private List<Product> products = new ArrayList<>();
    private Double max = 0.0;
    private Double min = 0.0;

}
