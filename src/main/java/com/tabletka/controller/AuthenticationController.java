package com.tabletka.controller;

import com.tabletka.exception.UserNotUniqueException;
import com.tabletka.model.apothecary.Apothecary;
import com.tabletka.model.client.Client;
import com.tabletka.service.impl.ApothecaryServiceImpl;
import com.tabletka.service.impl.ClientServiceImpl;
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

        return "auth/register";
    }

}
