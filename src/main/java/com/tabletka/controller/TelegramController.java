package com.tabletka.controller;

import com.tabletka.model.order.Order;
import com.tabletka.model.requests.TelegramChangeStatusRequest;
import com.tabletka.model.requests.TelegramLoginRequest;
import com.tabletka.model.responses.TelegramApothecaryChangeStatusResponse;
import com.tabletka.model.responses.TelegramApothecaryGetOrdersResponse;
import com.tabletka.model.responses.TelegramClientGetOrdersResponse;
import com.tabletka.model.responses.TelegramLoginResponse;
import com.tabletka.model.user.Role;
import com.tabletka.security.UserDetailsServiceImpl;
import com.tabletka.service.AuthService;
import lombok.AllArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TelegramController {

    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody TelegramLoginRequest request) {
        try {
            Role role = authService.registerUserFromTelegram(request);
            return ResponseEntity.ok(new TelegramLoginResponse(HttpStatus.SC_OK, role));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new TelegramLoginResponse(HttpStatus.SC_BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/client/{telegramId}/getOrders")
    public ResponseEntity<?> clientGetOrders(@PathVariable String telegramId) {
        try {
            // получаем заказы
            List<Order> orders = new ArrayList<>();
            return ResponseEntity.ok(new TelegramClientGetOrdersResponse(HttpStatus.SC_OK, orders));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new TelegramClientGetOrdersResponse(HttpStatus.SC_BAD_REQUEST, "No such client"));
        }
    }

    @GetMapping("/apothecary/{telegramId}/getOrders")
    public ResponseEntity<?> apothecaryGetOrders(@PathVariable String telegramId) {
        try {
            // получаем заказы
            List<Order> orders = new ArrayList<>();
            return ResponseEntity.ok(new TelegramApothecaryGetOrdersResponse(HttpStatus.SC_OK, orders));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new TelegramApothecaryGetOrdersResponse(HttpStatus.SC_BAD_REQUEST, "No such apothecary"));
        }
    }

    @PostMapping("/apothecary/changeStatus")
    public ResponseEntity<?> apothecaryChangeStatus(
            @RequestBody TelegramChangeStatusRequest request
    ) {
        try {
            // меняем статус заказа

            return ResponseEntity.ok(new TelegramApothecaryChangeStatusResponse(HttpStatus.SC_OK));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new TelegramApothecaryChangeStatusResponse(HttpStatus.SC_BAD_REQUEST, "Error"));
        }
    }
}
