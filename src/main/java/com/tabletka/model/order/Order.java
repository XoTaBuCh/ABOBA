package com.tabletka.model.order;

import com.tabletka.model.client.Client;
import com.tabletka.model.medicine.Medicine;
import com.tabletka.model.pharmacy.Pharmacy;
import com.tabletka.model.product.Product;
import com.tabletka.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double price;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pharmacy pharmacy;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();
}
