package com.tabletka.service.impl;

import com.tabletka.model.apothecary.Apothecary;
import com.tabletka.model.client.Client;
import com.tabletka.model.requests.TelegramLoginRequest;
import com.tabletka.model.user.Role;
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
    public Role registerUserFromTelegram(TelegramLoginRequest request) {
        Client client = clientRepository.findByEmail(request.getLogin());
        Apothecary apothecary = apothecaryRepository.findByEmail(request.getLogin());
        Role role;

        if (Objects.nonNull(apothecary)) {
            if (!passwordEncoder.matches(request.getPassword(), apothecary.getPassword())) {
                throw new UsernameNotFoundException("Invalid password");
            }
            apothecary.setTelegramId(request.getTelegramId());
            role = apothecary.getRole();
            apothecaryRepository.save(apothecary);
        } else if (Objects.nonNull(client)) {
            if (!passwordEncoder.matches(request.getPassword(), client.getPassword())) {
                throw new UsernameNotFoundException("Invalid password");
            }
            role = client.getRole();
            client.setTelegramId(request.getTelegramId());
            clientRepository.save(client);
        } else {
            throw new UsernameNotFoundException("User not found");
        }

        return role;
    }
}
