package com.tabletka.dto;

import com.tabletka.model.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long orderId;
    private OrderStatus status;
    private Double price;
    private List<String> products;
}
