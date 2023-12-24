package com.tabletka.service.impl;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.model.client.Client;
import com.tabletka.model.order.Order;
import com.tabletka.model.order.OrderItem;
import com.tabletka.model.order.OrderStatus;
import com.tabletka.model.pharmacy.Pharmacy;
import com.tabletka.model.product.Product;
import com.tabletka.repository.OrderItemRepository;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PharmacyRepository pharmacyRepository;
    private final AuthContextHandler authContextHandler;
    private final OrderItemRepository orderItemRepository;


    @Override
    public List<OrderItem> getCartForClient() throws UserIsNotLoggedInException {
        List<Order> orders = ((Client) authContextHandler.getLoggedInUser()).getOrders();
        return orders.stream()
                .filter(order -> order.getStatus().equals(OrderStatus.BUILD))
                .map(Order::getItems)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Double getOrderPrice() throws UserIsNotLoggedInException {
        List<Order> orders = ((Client) authContextHandler.getLoggedInUser()).getOrders();
        return orders.stream()
                .filter(order -> order.getStatus().equals(OrderStatus.BUILD))
                .map(Order::getPrice)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Order> getOrdersHistory() throws UserIsNotLoggedInException {
        List<Order> orders = ((Client) authContextHandler.getLoggedInUser()).getOrders();
        return orders.stream()
                .filter(order -> !order.getStatus().equals(OrderStatus.BUILD))
                .collect(Collectors.toList());
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
        Order order = orderRepository.findAllByClient(client).stream()
                .filter(ord -> ord.getStatus().equals(OrderStatus.BUILD))
                .findFirst()
                .orElse(null);

        if (Objects.isNull(order)) {
            order = new Order();
            order.setPrice((double) 0);
            order.setClient(client);
            order.setStatus(OrderStatus.BUILD);
            order.setPharmacy(product.getPharmacy());
        }

        order.setPrice(order.getPrice() + (product.getPrice() * amount));
        OrderItem orderItem = new OrderItem();
        orderItem.setAmount(amount);
        orderItem.setProduct(product);
        orderItem.setOrder(order);

        if (product.getAmount() < amount) {
            order.setStatus(OrderStatus.CANCELED);

        } else {
            product.setAmount(product.getAmount() - amount);
        }

        productRepository.save(product);
        orderRepository.save(order);
        orderItemRepository.save(orderItem);
    }

    @Override
    public List<Order> getActiveOrdersForPharmacy(Pharmacy pharmacy) {
        List<Order> orders = orderRepository.findOrdersByPharmacy(pharmacy);
        List<Order> newOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus() == OrderStatus.ACTIVE) {
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
        } else {
            order.setStatus(OrderStatus.CANCELED);
            for (OrderItem item : order.getItems()) {
                Product product = productRepository.findProductById(item.getProduct().getId());
                product.setAmount(product.getAmount() + item.getAmount());
                productRepository.save(product);
            }
        }
        orderRepository.save(order);
    }

    @Override
    public void changeCartStatus(String flag) throws UserIsNotLoggedInException {
        Client client = (Client) authContextHandler.getLoggedInUser();
        Order order = orderRepository.findAllByClient(client).stream()
                .filter(ord -> ord.getStatus().equals(OrderStatus.BUILD))
                .findFirst()
                .orElse(null);

        if (Objects.equals(flag, "true")) {
            if (Objects.nonNull(order)) {
                order.setStatus(OrderStatus.ACTIVE);
                orderRepository.save(order);
            }
        } else {
            if (Objects.nonNull(order)) {
                changeOrderInfo(String.valueOf(false), order.getId());
            }
        }

    }
}
