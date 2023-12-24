package com.tabletka.model.responses;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TelegramApothecaryChangeStatusResponse {
    private int status;
    public String error;

    public TelegramApothecaryChangeStatusResponse(int status) {
        this.status = status;
    }
}
