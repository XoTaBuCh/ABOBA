package com.tabletka.controller;

import com.tabletka.service.ApothecaryService;
import com.tabletka.service.ClientService;
import com.tabletka.service.PharmacyService;
import com.tabletka.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final RequestService requestService;
    private final PharmacyService pharmacyService;
    private final ClientService clientService;
    private final ApothecaryService apothecaryService;


    @GetMapping("")
    public String main() {
        return "redirect:admin/requests";
    }

    @GetMapping("/requests")
    public String getRequests(final Model model) {
        model.addAttribute("pharmacyCreatingRequests", requestService.getPharmacyCreatingRequests());
        return "admin/admin_requests";
    }

    @PostMapping("/requests")
    public String getRequests(final String flag, final Long requestId) {
        requestService.changeRequestState(flag, requestId);
        return "redirect:/admin/requests";
    }

    @GetMapping("/pharmacies")
    public String getPharmacies(final Model model) {
        model.addAttribute("pharmacies", pharmacyService.getPharmacies());
        return "admin/admin_pharmacies";
    }

    @PostMapping("/pharmacies")
    public String getPharmacies(final String name, final String address, final Long pharmacyId) {
        pharmacyService.changePharmacyNameAddress(name, address, pharmacyId);
        return "redirect:/admin/pharmacies";
    }

    @GetMapping("/users")
    public String getUsers(final Model model) {
        model.addAttribute("clients", clientService.getClients());
        model.addAttribute("apothecaries", apothecaryService.getApothecaries());
        return "admin/admin_users";
    }

    @PostMapping("/users")
    public String getUsers(final String flag, final String flag2, final Long userId) {
        if (Objects.equals(flag2, "client")) {
            clientService.changeClientStatus(flag, userId);
        } else if (Objects.equals(flag2, "apothecary")){
            apothecaryService.changeApothecaryStatus(flag, userId);
        }
        return "redirect:/admin/users";
    }
}