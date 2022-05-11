package com.tabletka.controller;

import com.tabletka.model.apothecary.Apothecary;
import com.tabletka.model.client.Client;
import com.tabletka.service.ApothecaryService;
import com.tabletka.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final ClientService clientService;
    private final ApothecaryService apothecaryService;


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
        clientService.register(client);
        return "auth/register";
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

        return "auth/register_apothecary";
    }
}
