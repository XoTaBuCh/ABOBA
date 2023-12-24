package com.tabletka.mapper;

import com.tabletka.dto.OrderDto;
import com.tabletka.model.order.Order;

import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderDto toOrderDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getStatus(),
                order.getPrice(),
                order.getItems().stream().map(orderItem ->
                                orderItem.getProduct().getMedicine().getName() + ", " + orderItem.getAmount().toString())
                        .collect(Collectors.toList())
        );
    }
}
