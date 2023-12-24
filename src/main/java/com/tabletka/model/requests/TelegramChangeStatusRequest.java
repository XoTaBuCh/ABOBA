package com.tabletka.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramChangeStatusRequest {
    private String orderId;
    private String telegramId;
    private String newStatus;
}
