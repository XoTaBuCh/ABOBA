package com.tabletka.service;
import com.tabletka.model.admin.Admin;
import com.tabletka.model.client.Client;
import com.tabletka.model.user.Role;
import com.tabletka.model.user.Status;
import com.tabletka.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    public void register(final Client client) {
        client.setStatus(Status.ACTIVE);
        client.setRole(Role.CLIENT);
        client.setPassword(encoder.encode(client.getPassword()));
        clientRepository.save(client);
    }
}
