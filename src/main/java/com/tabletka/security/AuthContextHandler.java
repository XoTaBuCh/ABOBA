package com.tabletka.security;

import com.tabletka.dto.PasswordChangeDTO;
import com.tabletka.exception.PasswordChangeException;
import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.model.admin.Admin;
import com.tabletka.model.apothecary.Apothecary;
import com.tabletka.model.client.Client;
import com.tabletka.model.user.User;
import com.tabletka.repository.AdminRepository;
import com.tabletka.repository.ApothecaryRepository;
import com.tabletka.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
public class AuthContextHandler {
    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final ApothecaryRepository apothecaryRepository;
    private final PasswordEncoder encoder;

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

    public void changePassword(PasswordChangeDTO passwordChangeDTO) throws PasswordChangeException, UserIsNotLoggedInException {
        if (!Objects.equals(passwordChangeDTO.getNewPassword(), passwordChangeDTO.getNewPasswordConfirmation())) {
            throw new PasswordChangeException("New passwords are different");
        }

        User user = getLoggedInUser();
        if (!encoder.matches(passwordChangeDTO.getOldPassword(), user.getPassword())) {
            throw new PasswordChangeException("Old password is wrong");
        }
        user.setPassword(encoder.encode(passwordChangeDTO.getNewPassword()));

        switch (user.getRole()) {
            case CLIENT -> {
                clientRepository.save((Client) user);
            }
            case APOTHECARY -> {
                apothecaryRepository.save((Apothecary) user);
            }
            case ADMIN -> {
                adminRepository.save((Admin) user);
            }
            default -> {
                throw new UserIsNotLoggedInException();
            }
        }
    }
}
