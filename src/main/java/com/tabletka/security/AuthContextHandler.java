package com.tabletka.security;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.model.user.User;
import com.tabletka.repository.AdminRepository;
import com.tabletka.repository.ApothecaryRepository;
import com.tabletka.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AuthContextHandler {
    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final ApothecaryRepository apothecaryRepository;

    public User getLoggedInUser() throws UserIsNotLoggedInException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : principal.toString();
        Optional<User> admin = Optional.ofNullable(adminRepository.findByEmail(email));
        Optional<User> client = Optional.ofNullable(clientRepository.findByEmail(email));
        Optional<User> apothecary = Optional.ofNullable(apothecaryRepository.findByEmail(email));
        if (!admin.isEmpty()) {
            return admin.get();
        } else if (!client.isEmpty()) {
            return client.get();
        } else if (!apothecary.isEmpty()) {
            return apothecary.get();
        } else {
            throw new UserIsNotLoggedInException();
        }
    }
}
