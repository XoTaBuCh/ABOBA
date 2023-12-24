package com.tabletka.service.impl;

import com.tabletka.model.requests.PharmacyCreationRequest;
import com.tabletka.repository.PharmacyCreatingRequestRepository;
import com.tabletka.service.PharmacyService;
import com.tabletka.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final PharmacyCreatingRequestRepository pharmacyCreatingRequestRepository;
    private final PharmacyService pharmacyService;

    @Override
    public List<PharmacyCreationRequest> getPharmacyCreatingRequests() {
        List<PharmacyCreationRequest> requests = pharmacyCreatingRequestRepository.findAll();
        if (requests.isEmpty()) return null;
        return requests;
    }

    @Override
    public void changeRequestState(String flag, Long requestId) {
        if (flag.equals("creation_true")) {
            PharmacyCreationRequest request =
                    pharmacyCreatingRequestRepository.findAPharmacyCreationRequestById(requestId);
            pharmacyService.addPharmacy(request.getName(), request.getAddress(), request.getApothecaryAdminId());
            pharmacyCreatingRequestRepository.deleteById(requestId);
        } else {
            pharmacyCreatingRequestRepository.deleteById(requestId);
        }
    }
}
