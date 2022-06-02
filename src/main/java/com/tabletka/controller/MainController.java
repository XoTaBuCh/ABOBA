package com.tabletka.controller;

import com.tabletka.dto.PasswordChangeDTO;
import com.tabletka.exception.PasswordChangeException;
import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.exception.UserNotUniqueException;
import com.tabletka.model.user.User;
import com.tabletka.security.AuthContextHandler;
import com.tabletka.service.MedicineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class MainController {

    private final AuthContextHandler authContextHandler;
    private final MedicineService medicineService;

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

    @GetMapping("/find")
    public String find(final Model model, String request) {
        model.addAttribute("medicines",
                medicineService.getMedicinesByName(request));
        return "main/main";
    }

    @GetMapping("/profile")
    public String profile(final Model model) throws UserIsNotLoggedInException {
        User user = authContextHandler.getLoggedInUser();
        model.addAttribute("user", user);
        model.addAttribute("result", null);
        return "main/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(final Model model, PasswordChangeDTO passwordChangeDTO) throws PasswordChangeException, UserIsNotLoggedInException {
        String result = "Password changed successfully";
        try {
            authContextHandler.changePassword(passwordChangeDTO);
        } catch(PasswordChangeException | UserIsNotLoggedInException e) {
            result = e.getMessage();
        }
        User user = authContextHandler.getLoggedInUser();
        model.addAttribute("user", user);
        model.addAttribute("result", result);

        return "main/profile";
    }

}