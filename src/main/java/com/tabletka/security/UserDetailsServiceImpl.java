package com.tabletka.security;

import com.tabletka.model.admin.Admin;
import com.tabletka.model.user.User;
import com.tabletka.repository.AdminRepository;
import com.tabletka.repository.ApothecaryRepository;
import com.tabletka.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final ApothecaryRepository apothecaryRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User admin = adminRepository.findByEmail(email);
        User client = clientRepository.findByEmail(email);
        User apothecary = apothecaryRepository.findByEmail(email);
        if (admin != null) {
            return admin.getUserDetails();
        } else if (client != null) {
            return client.getUserDetails();
        } else if (apothecary != null) {
            return apothecary.getUserDetails();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
