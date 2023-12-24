package com.tabletka.repository;

import com.tabletka.model.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);

    Client findClientById(Long userId);

    Client findClientByTelegramId(String telegramId);
}