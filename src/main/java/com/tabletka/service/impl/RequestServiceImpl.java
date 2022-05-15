package com.tabletka.service.impl;

import com.tabletka.model.requests.ApothecaryRegistrationRequest;
import com.tabletka.model.requests.PharmacyCreationRequest;
import com.tabletka.repository.PharmacyCreatingRequestRepository;
import com.tabletka.repository.RegistrationRequestRepository;
import com.tabletka.service.ApothecaryService;
import com.tabletka.service.PharmacyService;
import com.tabletka.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RegistrationRequestRepository registrationRequestRepository;
    private final PharmacyCreatingRequestRepository pharmacyCreatingRequestRepository;
    private final ApothecaryService apothecaryService;
    private final PharmacyService pharmacyService;
    @Override
    public List<ApothecaryRegistrationRequest> getRegistryRequests() {
        List<ApothecaryRegistrationRequest> requests = registrationRequestRepository.findAll();
        if (requests.isEmpty()) return null;
        return requests;
    }

    @Override
    public List<PharmacyCreationRequest> getPharmacyCreatingRequests() {
        List<PharmacyCreationRequest> requests = pharmacyCreatingRequestRepository.findAll();
        if (requests.isEmpty()) return null;
        return requests;
    }

    @Override
    public void changeRequestState(String flag, Long requestId) {
        switch (flag) {
            case "registration_true" -> {
               ApothecaryRegistrationRequest request =
                       registrationRequestRepository.findApothecaryRegistrationRequestById(requestId);
                apothecaryService.register(request.getEmail(), request.getPassword());
                registrationRequestRepository.deleteById(requestId);
            }
            case "registration_false" -> {
                registrationRequestRepository.deleteById(requestId);
            }
            case "creation_true" -> {
                PharmacyCreationRequest request =
                        pharmacyCreatingRequestRepository.findAPharmacyCreationRequestById(requestId);
                pharmacyService.addPharmacy(request.getName(), request.getAddress(), request.getApothecaryId());
                pharmacyCreatingRequestRepository.deleteById(requestId);
            }
            default -> {
                pharmacyCreatingRequestRepository.deleteById(requestId);
            }
        }
    }
}
