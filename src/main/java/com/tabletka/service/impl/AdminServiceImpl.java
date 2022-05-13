package com.tabletka.service.impl;

import com.tabletka.model.admin.Admin;
import com.tabletka.model.user.Role;
import com.tabletka.model.user.Status;
import com.tabletka.repository.AdminRepository;
import com.tabletka.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public void register(final Admin admin) {
        admin.setStatus(Status.ACTIVE);
        admin.setRole(Role.ADMIN);
        admin.setPassword(encoder.encode(admin.getPassword()));
        userRepository.save(admin);
    }
}
