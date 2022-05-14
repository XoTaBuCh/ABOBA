package com.tabletka.controller;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.exception.UserNotUniqueException;
import com.tabletka.model.apothecary.Apothecary;
import com.tabletka.model.client.Client;
import com.tabletka.model.medicine.Medicine;
import com.tabletka.service.impl.ApothecaryServiceImpl;
import com.tabletka.service.impl.ClientServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final ClientServiceImpl clientServiceImpl;
    private final ApothecaryServiceImpl apothecaryService;


    @GetMapping("")
    public String auth() {
        return "auth/auth";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(final Client client, final Model model) {
        String result = "successful";
        try {
            clientServiceImpl.register(client);
        } catch(UserNotUniqueException e) {
            result = "unsuccessful";
        }
        model.addAttribute("result", result);

        return "main/main";
    }

    @GetMapping("/register_apothecary")
    public String register_apothecary() {
        return "auth/register_apothecary";
    }

    @PostMapping("/register_apothecary")
    public String register_apothecary(final Apothecary apothecary, final Model model) {
        apothecaryService.register(apothecary);
        model.addAttribute("registerSuccess",
                "You applied for registration, after approving your request, you can sign in service");

        return "main/main";
    }
}
