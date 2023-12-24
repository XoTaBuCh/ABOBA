package com.tabletka.repository;

import com.tabletka.model.apothecary_admin.ApothecaryAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApothecaryAdminRepository extends JpaRepository<ApothecaryAdmin, Long> {
    ApothecaryAdmin findByEmail(String email);

    ApothecaryAdmin findApothecaryAdminById(Long Id);
}