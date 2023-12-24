package com.tabletka.service;

import com.tabletka.model.admin.Admin;
import com.tabletka.model.requests.TelegramLoginRequest;
import com.tabletka.model.user.Role;

public interface AuthService {
    Role registerUserFromTelegram(TelegramLoginRequest request);
}
