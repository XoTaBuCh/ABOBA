package com.tabletka.service.impl;

import com.tabletka.model.apothecary.Apothecary;
import com.tabletka.model.user.Role;
import com.tabletka.model.user.Status;
import com.tabletka.repository.ApothecaryRepository;
import com.tabletka.service.ApothecaryService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApothecaryServiceImpl implements ApothecaryService {
    private final ApothecaryRepository apothecaryRepository;
    private final PasswordEncoder encoder;

    @Override
    public void register(final Apothecary apothecary) {
        System.out.println(apothecary.getUsername());
        apothecary.setStatus(Status.ACTIVE);
        apothecary.setRole(Role.APOTHECARY);
        apothecary.setPassword(encoder.encode(apothecary.getPassword()));
        apothecaryRepository.save(apothecary);
    }
}
