package com.tabletka.service.impl;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.model.client.Client;
import com.tabletka.model.order.Order;
import com.tabletka.model.order.OrderStatus;
import com.tabletka.model.pharmacy.Pharmacy;
import com.tabletka.model.product.Product;
import com.tabletka.repository.OrderRepository;
import com.tabletka.repository.PharmacyRepository;
import com.tabletka.repository.ProductRepository;
import com.tabletka.security.AuthContextHandler;
import com.tabletka.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PharmacyRepository pharmacyRepository;
    private final AuthContextHandler authContextHandler;


    @Override
    public List<Order> getOrdersForClient() throws UserIsNotLoggedInException {
        return ((Client) authContextHandler.getLoggedInUser()).getOrders();
    }

    @Override
    public List<Order> getOrdersForPharmacy(Long pharmacyId) {
        Pharmacy pharmacy = pharmacyRepository.findPharmacyById(pharmacyId);
        return pharmacy.getOrders();
    }

    @Override
    public void makeOrder(Long productId, Long amount) throws UserIsNotLoggedInException {
        Client client = (Client) authContextHandler.getLoggedInUser();
        Product product = productRepository.findProductById(productId);

        Order order = new Order();
        order.setClient(client);
        order.setAmount(amount);
        order.setPharmacy(product.getPharmacy());
        order.setProduct(product);
        order.setPrice(product.getPrice() * amount);

        if (product.getAmount() < amount) {
            order.setStatus(OrderStatus.CANCELED);

        } else {
            order.setStatus(OrderStatus.ACTIVE);
            product.setAmount(product.getAmount() - amount);
        }

        productRepository.save(product);
        orderRepository.save(order);
    }

    @Override
    public List<Order> getActiveOrdersForPharmacy(Pharmacy pharmacy) {
        List<Order> orders = orderRepository.findOrdersByPharmacy(pharmacy);
        List<Order> newOrders = new ArrayList<>();
        for (Order order: orders) {
            if (order.getStatus() == OrderStatus.ACTIVE){
                newOrders.add(order);
            }
        }
        return newOrders;
    }

    @Override
    public void changeOrderInfo(String flag, Long orderId) {
        Order order = orderRepository.findOrderById(orderId);

        if (Objects.equals(flag, "true")) {
            order.setStatus(OrderStatus.DONE);
        }
        else {
            order.setStatus(OrderStatus.CANCELED);
            Product product = productRepository.findProductById(order.getProduct().getId());
            product.setAmount(product.getAmount() + order.getAmount());
            productRepository.save(product);
        }
        orderRepository.save(order);
    }
}
