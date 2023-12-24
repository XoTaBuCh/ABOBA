package com.tabletka.service.impl;

import com.tabletka.model.requests.TelegramLoginRequest;
import com.tabletka.model.user.User;
import com.tabletka.repository.AdminRepository;
import com.tabletka.repository.ApothecaryRepository;
import com.tabletka.repository.ClientRepository;
import com.tabletka.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final ClientRepository clientRepository;
    private final ApothecaryRepository apothecaryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String registerUserFromTelegram(TelegramLoginRequest request) {
        User client = clientRepository.findByEmail(request.getLogin());
        User apothecary = apothecaryRepository.findByEmail(request.getLogin());
        String role;

        if (Objects.nonNull(apothecary)) {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            if (!encodedPassword.equals(apothecary.getPassword())) {
                throw new UsernameNotFoundException("Invalid password");
            }
            apothecary.setTelegramId(request.getTelegramId());
            role = apothecary.getRole().toString();
        } else if (Objects.nonNull(client)) {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            if (!encodedPassword.equals(client.getPassword())) {
                throw new UsernameNotFoundException("Invalid password");
            }
            role = client.getRole().toString();
            client.setTelegramId(request.getTelegramId());
        } else {
            throw new UsernameNotFoundException("User not found");
        }

        return role;
    }
}
