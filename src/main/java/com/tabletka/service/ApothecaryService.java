package com.tabletka.service;

import com.tabletka.model.apothecary.Apothecary;
import com.tabletka.model.user.Role;
import com.tabletka.model.user.Status;
import com.tabletka.repository.ApothecaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApothecaryService {
    private final ApothecaryRepository apothecaryRepository;
    private final PasswordEncoder encoder;

    public void register(final Apothecary apothecary) {
        apothecary.setStatus(Status.ACTIVE);
        apothecary.setRole(Role.APOTHECARY);
        apothecary.setPassword(encoder.encode(apothecary.getPassword()));
        apothecaryRepository.save(apothecary);
    }
}
