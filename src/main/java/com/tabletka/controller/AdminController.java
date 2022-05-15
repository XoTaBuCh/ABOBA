package com.tabletka.controller;

import com.tabletka.service.RequestService;
import com.tabletka.service.impl.RequestServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final RequestService requestService;
    @GetMapping("")
    public String main() {
        return "admin/admin_main";
    }

    @GetMapping("/requests")
    public String getRequests(final Model model) {
        model.addAttribute("registryRequests", requestService.getRegistryRequests());
        model.addAttribute("pharmacyCreatingRequests", requestService.getPharmacyCreatingRequests());
        return "admin/requests";
    }

    @PostMapping("/requests")
    public String getRequests(final String flag, final Long requestId) {
        requestService.changeRequestState(flag, requestId);
        return "redirect:/admin/requests";
    }
}