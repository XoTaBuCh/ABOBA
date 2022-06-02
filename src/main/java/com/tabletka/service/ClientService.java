package com.tabletka.service;

import com.tabletka.model.client.Client;

import java.util.List;

public interface ClientService {
    void register(Client client);
    List<Client> getClients();

    void changeClientStatus(String flag, Long userId);
}
