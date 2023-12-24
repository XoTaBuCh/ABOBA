package com.tabletka.model.responses;

import com.tabletka.dto.OrderDto;
import com.tabletka.model.order.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class TelegramApothecaryGetOrdersResponse {
    private int status;
    private String error;
    private List<OrderDto> orders;

    public TelegramApothecaryGetOrdersResponse(int status, List<OrderDto> orders) {
        this.status = status;
        this.orders = orders;
    }

    public TelegramApothecaryGetOrdersResponse(int status, String error) {
        this.status = status;
        this.error = error;
    }
}
