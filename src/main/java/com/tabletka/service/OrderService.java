package com.tabletka.service;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.model.order.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrdersForClient() throws UserIsNotLoggedInException;

}
