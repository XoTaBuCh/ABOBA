package com.tabletka.service.impl;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.model.client.Client;
import com.tabletka.model.order.Order;
import com.tabletka.model.user.User;
import com.tabletka.repository.ClientRepository;
import com.tabletka.repository.OrderRepository;
import com.tabletka.repository.PharmacyRepository;
import com.tabletka.security.AuthContextHandler;
import com.tabletka.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final PharmacyRepository pharmacyRepository;
    private final AuthContextHandler authContextHandler;

    @Override
    public List<Order> getOrdersForClient() throws UserIsNotLoggedInException {
        return ((Client) authContextHandler.getLoggedInUser()).getOrders();
    }
}
