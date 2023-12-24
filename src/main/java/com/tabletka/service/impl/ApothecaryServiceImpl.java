package com.tabletka.service.impl;

import com.tabletka.model.apothecary.Apothecary;
import com.tabletka.model.pharmacy.Pharmacy;
import com.tabletka.model.user.Role;
import com.tabletka.model.user.Status;
import com.tabletka.repository.ApothecaryRepository;
import com.tabletka.service.ApothecaryService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ApothecaryServiceImpl implements ApothecaryService {
    private final ApothecaryRepository apothecaryRepository;
    private final PharmacyServiceImpl pharmacyService;
    private final PasswordEncoder encoder;

    @Override
    public void register(final Apothecary apothecary, Long pharmacyId) {
        Pharmacy pharmacy = pharmacyService.getPharmacyById(pharmacyId);

        apothecary.setStatus(Status.ACTIVE);
        apothecary.setRole(Role.APOTHECARY);
        apothecary.setPassword(encoder.encode(apothecary.getPassword()));
        apothecary.setEmail(apothecary.getEmail());
        apothecary.setPharmacy(pharmacy);
        apothecaryRepository.save(apothecary);
    }

    @Override
    public List<Apothecary> getApothecaries() {
        return apothecaryRepository.findAll();
    }

    @Override
    public void changeApothecaryStatus(String flag, Long userId) {
        Apothecary apothecary = apothecaryRepository.findApothecaryById(userId);
        if (Objects.equals(flag, "unban")) {
            apothecary.setStatus(Status.ACTIVE);
        } else if (Objects.equals(flag, "ban")){
            apothecary.setStatus(Status.BANNED);
        }
        apothecaryRepository.save(apothecary);
    }
}
