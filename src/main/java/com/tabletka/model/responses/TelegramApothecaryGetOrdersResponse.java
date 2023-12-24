package com.tabletka.model.responses;

import com.tabletka.model.order.Order;

import java.util.List;

public class TelegramApothecaryGetOrdersResponse {
    private int status;
    public String error;
    private List<Order> orders;

    public TelegramApothecaryGetOrdersResponse(int status, List<Order> orders) {
        this.status = status;
        this.orders = orders;
    }

    public TelegramApothecaryGetOrdersResponse(int status, String error) {
        this.status = status;
        this.error = error;
    }
}
