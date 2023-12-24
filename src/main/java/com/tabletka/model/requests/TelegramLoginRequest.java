package com.tabletka.model.requests;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class TelegramLoginRequest {
    private String login;
    private String password;
    private String telegramId;
}
