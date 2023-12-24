package com.tabletka.model.responses;

import com.tabletka.model.order.Order;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class TelegramClientGetOrdersResponse {
    private int status;
    public String error;
    private List<Order> orders;

    public TelegramClientGetOrdersResponse(int status, List<Order> orders) {
        this.status = status;
        this.orders = orders;
    }

    public TelegramClientGetOrdersResponse(int status, String error) {
        this.status = status;
        this.error = error;
    }
}

