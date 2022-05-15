package com.tabletka.service;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.model.order.Order;
import com.tabletka.model.pharmacy.Pharmacy;

import java.util.List;

public interface OrderService {
    List<Order> getOrdersForClient() throws UserIsNotLoggedInException;

    List<Order> getOrdersForPharmacy(Long pharmacyId);

    void makeOrder(Long ProductId, Long amount) throws UserIsNotLoggedInException;

    List<Order> getActiveOrdersForPharmacy(Pharmacy pharmacy);

    void changeOrderInfo(String flag, Long orderId);
}
