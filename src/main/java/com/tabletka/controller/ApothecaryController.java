package com.tabletka.controller;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.model.apothecary.Apothecary;
import com.tabletka.model.pharmacy.Pharmacy;
import com.tabletka.service.PharmacyService;
import com.tabletka.service.impl.PharmacyServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apothecary")
@AllArgsConstructor
public class ApothecaryController {

    private final PharmacyService pharmacyService;

    @GetMapping("")
    public String main(final Model model) throws UserIsNotLoggedInException {
        model.addAttribute("pharmacies",
                pharmacyService.getPharmaciesForApothecary());
        return "apothecary/apothecary_main";
    }


    @GetMapping("/add_pharmacy")
    public String register_apothecary() {
        return "apothecary/add_pharmacy";
    }

    @PostMapping("/add_pharmacy")
    public String register_apothecary(final Pharmacy pharmacy, final Model model) throws UserIsNotLoggedInException {
        pharmacyService.addPharmacy(pharmacy);
        model.addAttribute("registerSuccess",
                "true");

        return "apothecary/add_pharmacy";
    }
}