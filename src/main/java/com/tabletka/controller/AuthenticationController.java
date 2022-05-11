package com.tabletka.controller;

import com.tabletka.model.user.User;
import com.tabletka.service.UserService;
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
    private final UserService userService;

    @GetMapping("")
    public String auth() {
        return "auth";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(final User user, final Model model) {
        userService.register(user);
        model.addAttribute("registerSuccess",
                "You applied for registration, after approving your request, you can sign in service");

        return "register";
    }

}
