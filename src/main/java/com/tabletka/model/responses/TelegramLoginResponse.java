package com.tabletka.model.responses;

import com.tabletka.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class TelegramLoginResponse {
    public int status;
    public String error;

    public Role role;

    public TelegramLoginResponse(int status, Role role) {
        this.status = status;
        this.role = role;
    }

    public TelegramLoginResponse(int status, String error) {
        this.status = status;
        this.error = error;
    }
}
