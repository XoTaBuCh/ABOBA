package com.tabletka.repository;

import com.tabletka.model.requests.ApothecaryRegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRequestRepository extends JpaRepository<ApothecaryRegistrationRequest, Long> {
    ApothecaryRegistrationRequest findApothecaryRegistrationRequestById(Long requestId);
}
