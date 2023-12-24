package com.tabletka.model.responses;

import com.tabletka.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TelegramLoginResponse {
    private int status;
    private String error;

    private Role role;

    public TelegramLoginResponse(int status, Role role) {
        this.status = status;
        this.role = role;
    }

    public TelegramLoginResponse(int status, String error) {
        this.status = status;
        this.error = error;
    }
}
