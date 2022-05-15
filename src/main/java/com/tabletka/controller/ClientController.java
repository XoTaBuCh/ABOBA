package com.tabletka.controller;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.service.impl.MedicineServiceImpl;
import com.tabletka.service.impl.OrderServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {

    private MedicineServiceImpl medicineServiceImpl;
    private OrderServiceImpl orderServiceImpl;

    @GetMapping("")
    public String main() {
        return "client/client_main";
    }

    @GetMapping("/shopping_cart")
    public String shoppingCart(final Model model) throws UserIsNotLoggedInException {
        model.addAttribute("orders",
                orderServiceImpl.getOrdersForClient());
        return "client/shopping_cart";
    }

    @GetMapping("/find")
    public String find(final Model model, String request) {
        model.addAttribute("medicines",
                medicineServiceImpl.getMedicinesByName(request));
        return "client/client_main";
    }



}