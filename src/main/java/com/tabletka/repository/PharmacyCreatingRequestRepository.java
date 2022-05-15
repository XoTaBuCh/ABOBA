package com.tabletka.repository;

import com.tabletka.model.requests.ApothecaryRegistrationRequest;
import com.tabletka.model.requests.PharmacyCreationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyCreatingRequestRepository extends JpaRepository<PharmacyCreationRequest, Long> {
    PharmacyCreationRequest findAPharmacyCreationRequestById(Long requestId);
}
