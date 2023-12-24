package com.tabletka.model.responses;

import com.tabletka.dto.OrderDto;
import com.tabletka.model.order.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TelegramClientGetOrdersResponse {
    private int status;
    private String error;
    private List<OrderDto> orders;

    public TelegramClientGetOrdersResponse(int status, List<OrderDto> orders) {
        this.status = status;
        this.orders = orders;
    }

    public TelegramClientGetOrdersResponse(int status, String error) {
        this.status = status;
        this.error = error;
    }
}

