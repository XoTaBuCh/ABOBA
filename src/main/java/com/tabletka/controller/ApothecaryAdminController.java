package com.tabletka.controller;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.model.apothecary.Apothecary;
import com.tabletka.model.pharmacy.Pharmacy;
import com.tabletka.service.ApothecaryService;
import com.tabletka.service.PharmacyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apothecary_admin")
@AllArgsConstructor
public class ApothecaryAdminController {

    private final PharmacyService pharmacyService;
    private final ApothecaryService apothecaryService;

    @GetMapping("")
    public String main(final Model model) throws UserIsNotLoggedInException {
        model.addAttribute("pharmacies",
                pharmacyService.getPharmaciesForAdminApothecary());
        return "apothecary/apothecary_main";
    }


    @GetMapping("/add_pharmacy")
    public String add_pharmacy() {
        return "apothecary/add_pharmacy";
    }

    @PostMapping("/add_pharmacy")
    public String createPharmacy(final Pharmacy pharmacy, final Model model) throws UserIsNotLoggedInException {
        pharmacyService.addPharmacy(pharmacy);
        model.addAttribute("registerSuccess",
                "true");

        return "apothecary/add_pharmacy";
    }

    @GetMapping("/register_apothecary")
    public String registerApothecary(final Model model) throws UserIsNotLoggedInException {
        model.addAttribute("pharmacies",
                pharmacyService.getPharmaciesForAdminApothecary());

        return "apothecaryAdmin/register_apothecary";
    }

    @PostMapping("/register_apothecary")
    public String createApothecary(final Apothecary apothecary, final Long pharmacyId) {
        apothecaryService.register(apothecary, pharmacyId);
        return "redirect:/main";
    }
}