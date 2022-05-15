package com.tabletka.repository;

import com.tabletka.model.order.Order;
import com.tabletka.model.pharmacy.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrdersByPharmacy(Pharmacy pharmacy);

    Order findOrderById(Long orderId);
}