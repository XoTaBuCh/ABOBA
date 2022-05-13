package com.tabletka.controller;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.model.medicine.Medicine;
import com.tabletka.model.user.User;
import com.tabletka.security.AuthContextHandler;
import com.tabletka.service.MedicineService;
import com.tabletka.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class MainController {

    private final AuthContextHandler authContextHandler;
    private final MedicineService medicineService;
    private final OrderService orderService;

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
                medicineService.getMedicines(request));
        return "main/main";
    }

    @GetMapping("/medicine/{id}")
    public String mainMedicine(@PathVariable Long id, Model model) throws UserIsNotLoggedInException {
        User user;
        try {
            user = authContextHandler.getLoggedInUser();
        } catch (UserIsNotLoggedInException e) {
            user = null;
        }
        Medicine medicine = medicineService.getMedicineById(id);
        model.addAttribute("user", user);
        model.addAttribute("medicine", medicine);
        model.addAttribute("products", medicine.getProducts().isEmpty() ? null : medicine.getProducts());

        return "medicine/medicine";
    }

    @PostMapping("/medicine/{id}")
    public String mainMedicine(@PathVariable Long id, Long amount, Long productId) throws UserIsNotLoggedInException {
        Medicine medicine = medicineService.getMedicineById(id);
        orderService.makeOrder(productId, amount);
        return "redirect:/medicine/" + id.toString();
    }

}