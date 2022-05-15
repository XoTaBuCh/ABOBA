package com.tabletka.service;

import com.tabletka.model.requests.ApothecaryRegistrationRequest;
import com.tabletka.model.requests.PharmacyCreationRequest;

import java.util.List;

public interface RequestService {
    List<ApothecaryRegistrationRequest> getRegistryRequests();

    List<PharmacyCreationRequest> getPharmacyCreatingRequests();

    void changeRequestState(String flag, Long requestId);
}
