package com.tabletka.service;

import com.tabletka.model.user.Role;
import com.tabletka.model.user.Status;
import com.tabletka.model.user.User;
import com.tabletka.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public void register(final User user) {
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.CLIENT);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
