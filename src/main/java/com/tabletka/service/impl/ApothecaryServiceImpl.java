package com.tabletka.service.impl;

import com.tabletka.model.apothecary.Apothecary;
import com.tabletka.model.requests.ApothecaryRegistrationRequest;
import com.tabletka.model.requests.RequestStatus;
import com.tabletka.model.user.Role;
import com.tabletka.model.user.Status;
import com.tabletka.repository.ApothecaryRepository;
import com.tabletka.repository.RegistrationRequestRepository;
import com.tabletka.service.ApothecaryService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApothecaryServiceImpl implements ApothecaryService {
    private final ApothecaryRepository apothecaryRepository;
    private final RegistrationRequestRepository requestRepository;
    private final PasswordEncoder encoder;

    @Override
    public void register(final Apothecary apothecary) {
        ApothecaryRegistrationRequest request = new ApothecaryRegistrationRequest();
        request.setRequestStatus(RequestStatus.PENDING);
        request.setPassword(encoder.encode(apothecary.getPassword()));
        request.setEmail(apothecary.getEmail());
        requestRepository.save(request);
    }

    @Override
    public void register(String email, String password) {
        Apothecary apothecary = new Apothecary();
        apothecary.setStatus(Status.ACTIVE);
        apothecary.setRole(Role.APOTHECARY);
        apothecary.setPassword(password);
        apothecary.setEmail(email);
        apothecaryRepository.save(apothecary);
    }
}
