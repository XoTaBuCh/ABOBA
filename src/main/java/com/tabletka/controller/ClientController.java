package com.tabletka.controller;

import com.tabletka.service.impl.MedicineServiceImpl;
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

    @GetMapping("")
    public String main() {
        return "client/client_main";
    }

    @GetMapping("/shopping_cart")
    public String shoppingCart() {
        return "client/shopping_cart";
    }

    @GetMapping("/find")
    public String find(final Model model, String request) {
        model.addAttribute("founded",
                medicineServiceImpl.getMedicines(request));
        System.out.println(medicineServiceImpl.getMedicines(request));
        return "client/client_main";
    }



}