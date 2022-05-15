package com.tabletka.controller;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.model.client.Client;
import com.tabletka.model.medicine.Medicine;
import com.tabletka.model.medicine.MedicinesType;
import com.tabletka.model.pharmacy.Pharmacy;
import com.tabletka.model.product.Product;
import com.tabletka.model.user.User;
import com.tabletka.security.AuthContextHandler;
import com.tabletka.service.MedicineService;
import com.tabletka.service.OrderService;
import com.tabletka.service.PharmacyService;
import com.tabletka.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pharmacy")
@AllArgsConstructor
public class PharmacyController {

    private final AuthContextHandler authContextHandler;
    private final PharmacyService pharmacyService;
    private final ProductService productService;
    private final MedicineService medicineService;
    private final OrderService orderService;


    @GetMapping("/{id}")
    public String mainPharmacy(@PathVariable Long id, Model model) {
        User user;
        try {
            user = authContextHandler.getLoggedInUser();
            if(user.getClass() == Client.class){
                user = null;
            }
        } catch (UserIsNotLoggedInException e) {
            user = null;
        }
        Pharmacy pharmacy = pharmacyService.getPharmacyById(id);
        List<Product> products = productService.getProductsForPharmacy(pharmacy);
        model.addAttribute("user", user);
        model.addAttribute("pharmacy", pharmacy);
        model.addAttribute("products", products.isEmpty() ? null : products);

        return "pharmacy/pharmacy";
    }

    @PostMapping("/{id}")
    public String mainPharmacy(@PathVariable Long id, Long amount, Double price, Long productId) {
        pharmacyService.changeProductInfo(productId, amount, price);
        return "redirect:/pharmacy/" + id.toString();
    }

    @GetMapping("/{phId}/add_product")
    public String addProductPharmacy(@PathVariable Long phId, Model model) {
        model.addAttribute("pharmacyId", phId);
        model.addAttribute("medicines", medicineService.getAllMedicines());
        model.addAttribute("types", MedicinesType.class.getEnumConstants());
        return "pharmacy/add_product";
    }

    @PostMapping("/{phId}/add_product")
    public String addProductPharmacy(@PathVariable Long phId, Medicine newMedicine, Long amount, Double price) {

        if (newMedicine.getId() == null) {
            medicineService.addMedicine(newMedicine);
        } else {
            newMedicine = medicineService.getMedicineById(newMedicine.getId());
        }

        pharmacyService.addProduct(phId, newMedicine.getId(), amount, price);

        return "redirect:/pharmacy/" + phId.toString() + "/add_product";
    }

    @GetMapping("/{phId}/orders")
    public String getOrders(@PathVariable Long phId, Model model) {
        model.addAttribute("orders", pharmacyService.getPharmacyOrders(phId));
        return "pharmacy/orders";
    }

    @PostMapping("/{phId}/orders")
    public String getOrders(@PathVariable Long phId, String flag, Long orderId) {
        orderService.changeOrderInfo(flag, orderId);
        return "redirect:/pharmacy/" + phId.toString() + "/orders";
    }

}
