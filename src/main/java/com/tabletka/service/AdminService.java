package com.tabletka.service;

import com.tabletka.model.admin.Admin;
import com.tabletka.model.user.Role;
import com.tabletka.model.user.Status;
import com.tabletka.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AdminService {
    private final AdminRepository userRepository;
    private final PasswordEncoder encoder;

    public void register(final Admin admin) {
        admin.setStatus(Status.ACTIVE);
        admin.setRole(Role.ADMIN);
        admin.setPassword(encoder.encode(admin.getPassword()));
        userRepository.save(admin);
    }
}
