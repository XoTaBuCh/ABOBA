package com.tabletka.model.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TelegramApothecaryChangeStatusResponse {
    private int status;
    private String error;

    public TelegramApothecaryChangeStatusResponse(int status) {
        this.status = status;
    }
}
