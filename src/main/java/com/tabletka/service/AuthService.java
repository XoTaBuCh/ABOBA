package com.tabletka.service;

import com.tabletka.model.admin.Admin;
import com.tabletka.model.requests.TelegramLoginRequest;

public interface AuthService {
    String registerUserFromTelegram(TelegramLoginRequest request);
}
