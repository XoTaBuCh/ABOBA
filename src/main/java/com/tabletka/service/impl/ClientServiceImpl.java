package com.tabletka.service.impl;
import com.tabletka.model.admin.Admin;
import com.tabletka.model.apothecary.Apothecary;
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

import java.util.List;
import java.util.Objects;

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
    }

    @Override
    public List<Client> getClients(){
        return clientRepository.findAll();
    }

    @Override
    public void changeClientStatus(String flag, Long userId) {
        Client client = clientRepository.findClientById(userId);
        if (Objects.equals(flag, "unban")) {
            client.setStatus(Status.ACTIVE);
        } else if (Objects.equals(flag, "ban")){
            client.setStatus(Status.BANNED);
        }
        clientRepository.save(client);
    }

}
