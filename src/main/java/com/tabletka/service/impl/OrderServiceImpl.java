package com.tabletka.service.impl;

import com.tabletka.exception.UserIsNotLoggedInException;
import com.tabletka.model.client.Client;
import com.tabletka.model.order.Order;
import com.tabletka.model.order.OrderStatus;
import com.tabletka.model.pharmacy.Pharmacy;
import com.tabletka.model.product.Product;
import com.tabletka.repository.ClientRepository;
import com.tabletka.repository.OrderRepository;
import com.tabletka.repository.PharmacyRepository;
import com.tabletka.repository.ProductRepository;
import com.tabletka.security.AuthContextHandler;
import com.tabletka.service.OrderService;
import lombok.AllArgsConstructor;
import org.aspectj.apache.bcel.generic.InstructionConstants;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).get();
        return pharmacy.getOrders();
    }

    @Override
    public void makeOrder(Long ProductId, int amount) throws UserIsNotLoggedInException {
        Client client = (Client) authContextHandler.getLoggedInUser();
        Product product = productRepository.findById(ProductId).get();

        Order order = new Order();
        order.setClient(client);
        order.setAmount(amount);
        order.setStatus(OrderStatus.ACTIVE);
        order.setPharmacy(product.getPharmacy());
        order.setProduct(product);
        order.setPrice(product.getPrice() * amount);

        orderRepository.save(order);
    }
}
