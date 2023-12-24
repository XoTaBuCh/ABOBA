package com.tabletka.controller;

import com.tabletka.dto.OrderDto;
import com.tabletka.mapper.OrderMapper;
import com.tabletka.model.order.OrderStatus;
import com.tabletka.model.requests.TelegramChangeStatusRequest;
import com.tabletka.model.requests.TelegramLoginRequest;
import com.tabletka.model.responses.TelegramApothecaryChangeStatusResponse;
import com.tabletka.model.responses.TelegramApothecaryGetOrdersResponse;
import com.tabletka.model.responses.TelegramClientGetOrdersResponse;
import com.tabletka.model.responses.TelegramLoginResponse;
import com.tabletka.model.user.Role;
import com.tabletka.service.AuthService;
import com.tabletka.service.OrderService;
import lombok.AllArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TelegramController {
    private OrderService orderService;
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
            List<OrderDto> orders = orderService.getTelegramOrdersHistory(telegramId)
                    .stream().map(OrderMapper::toOrderDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new TelegramClientGetOrdersResponse(HttpStatus.SC_OK, orders));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new TelegramClientGetOrdersResponse(HttpStatus.SC_BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/apothecary/{telegramId}/getOrders")
    public ResponseEntity<?> apothecaryGetOrders(@PathVariable String telegramId) {
        try {
            List<OrderDto> orders = orderService.getTelegramOrdersHistory(telegramId)
                    .stream().map(OrderMapper::toOrderDto)
                    .collect(Collectors.toList());
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
            if (request.getNewStatus().equals(OrderStatus.DONE.toString())) {
                orderService.changeOrderInfo("true", Long.valueOf(request.getOrderId()));
            } else {
                orderService.changeOrderInfo("false", Long.valueOf(request.getOrderId()));
            }

            return ResponseEntity.ok(new TelegramApothecaryChangeStatusResponse(HttpStatus.SC_OK));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new TelegramApothecaryChangeStatusResponse(HttpStatus.SC_BAD_REQUEST, e.getMessage()));
        }
    }
}
