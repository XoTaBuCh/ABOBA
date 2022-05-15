package com.tabletka.service.impl;
import com.tabletka.model.admin.Admin;
import com.tabletka.model.client.Client;
import com.tabletka.model.user.Role;
import com.tabletka.model.user.Status;
import com.tabletka.repository.AdminRepository;
import com.tabletka.repository.ClientRepository;
import com.tabletka.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    @Override
    public void register(final Client client) {
        client.setStatus(Status.ACTIVE);
        client.setRole(Role.CLIENT);
        client.setPassword(encoder.encode(client.getPassword()));
        clientRepository.save(client);
        //Admin admin = new Admin();
        //admin.setPassword(encoder.encode(client.getPassword()));
        //admin.setRole(Role.ADMIN);
        //admin.setStatus(Status.ACTIVE);
        //admin.setEmail(client.getEmail());
        //adminRepository.save(admin);
    }

}
