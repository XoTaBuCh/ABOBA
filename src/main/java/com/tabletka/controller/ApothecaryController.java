package com.tabletka.controller;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.service.impl.PharmacyServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apothecary")
@AllArgsConstructor
public class ApothecaryController {

    private final PharmacyServiceImpl pharmacyServiceImpl;

    @GetMapping("")
    public String main(final Model model) throws UserIsNotLoggedInException {
        model.addAttribute("pharmacies",
                pharmacyServiceImpl.getPharmaciesForApothecary());
        return "apothecary/apothecary_main";
    }
}