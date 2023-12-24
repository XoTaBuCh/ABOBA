package com.tabletka.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelegramLoginRequest {
    private String login;
    private String password;
    private String telegramId;
}
