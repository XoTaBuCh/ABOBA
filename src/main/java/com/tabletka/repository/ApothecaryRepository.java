package com.tabletka.repository;

import com.tabletka.model.apothecary.Apothecary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApothecaryRepository extends JpaRepository<Apothecary, Long> {
    Apothecary findByEmail(String email);
    Apothecary findApothecaryById(Long Id);

    Apothecary findApothecaryByTelegramId(String telegramId);
}
