package com.tabletka.dto;

import com.tabletka.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForecastDto {
    private Product product;
    private List<Long> forecast;
}
