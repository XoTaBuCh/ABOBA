package com.tabletka.controller;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.model.user.Role;
import com.tabletka.model.user.User;
import com.tabletka.security.AuthContextHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class MainController {

    private final AuthContextHandler authContextHandler;
    @GetMapping("")
    public String main() {
        try {
            final User user = authContextHandler.getLoggedInUser();
            switch (user.getRole()) {
                case CLIENT -> {
                    return "redirect:client";
                }
                case APOTHECARY -> {
                    return "redirect:apothecary";
                }
                case ADMIN -> {
                    return "redirect:admin";
                }
                default -> {
                    return "main/main";
                }
            }
        } catch (UserIsNotLoggedInException e) {
            return "main/main";
        }
    }



}