package com.tabletka.model.requests;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TelegramChangeStatusRequest {
    private String orderId;
    private String telegramId;
    private String newStatus;
}
